package com.example.icommerce.services;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;

import org.hibernate.internal.util.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.icommerce.dtos.ProductDTO;
import com.example.icommerce.entities.Product;
import com.example.icommerce.entities.ProductPrice;
import com.example.icommerce.exceptions.ICommerceException;
import com.example.icommerce.mappers.ProductMapper;
import com.example.icommerce.models.ProductPagingRequest;
import com.example.icommerce.models.ProductRequestModel;
import com.example.icommerce.repositories.ProductPriceRepository;
import com.example.icommerce.repositories.ProductRepository;
import com.example.icommerce.utilities.ProductSpecification;
import com.example.icommerce.utilities.SkuGenerator;

@Service
@Transactional
public class ProductService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductPriceRepository productPriceRepository;

    public List<ProductPrice> getAllProductPrices (String productSku) {
        if ( StringHelper.isEmpty(productSku) ) {
            return Collections.emptyList();
        }

        return productPriceRepository.findAllByProduct_Sku(productSku);
    }

    protected ProductPrice getLatestPrice (@NotEmpty String productSku) {
        List<ProductPrice> productPrices = getAllProductPrices(productSku);
        if ( productPrices.isEmpty() ) {
            return null;
        }

        return productPrices.stream().min((t0, t1) -> t1.getModifiedDate().compareTo(t0.getModifiedDate())).orElse(null);
    }

    public Page<ProductDTO> getProducts (ProductPagingRequest pagingRequest) {
        Pageable pageable;

        if ( StringHelper.isEmpty(pagingRequest.getSortBy()) ) {
            pageable = PageRequest.of(pagingRequest.getPage(), pagingRequest.getLimit(), Sort.by(Sort.Order.desc("createdDate")));
        }
        else {
            Sort sort = Sort.by(Sort.Order.asc(pagingRequest.getSortBy()));
            if ( pagingRequest.getSortBy().startsWith("-") ) {
                sort = Sort.by(Sort.Order.desc(pagingRequest.getSortBy().substring(1)));
            }

            pageable = PageRequest.of(pagingRequest.getPage(), pagingRequest.getLimit(), sort);
        }

        Page<Product> productPages = productRepository.findAll(buildFilter(pagingRequest), pageable);
        List<ProductDTO> productDTOs = productPages.getContent().stream().map(ProductMapper.INSTANCE::toDTO).collect(Collectors.toList());

        return new PageImpl<>(productDTOs, pageable, productPages.getTotalElements());

    }

    private Specification<Product> appendSpecification (Specification<Product> root, Specification<Product> child, boolean isOrPredicate) {
        if ( root == null ) {
            return child;
        }
        return isOrPredicate ? Specification.where(root).or(child) : Specification.where(root).and(child);
    }

    private Specification<Product> appendSpecification (Specification<Product> root, Specification<Product> child) {
        return appendSpecification(root, child, false);
    }

    private Specification<Product> buildFilter (ProductPagingRequest pagingRequest) {
        Specification<Product> specification = null;
        if ( StringHelper.isNotEmpty(pagingRequest.getProductSku()) ) {
            specification = appendSpecification(specification, ProductSpecification.skuEqual(pagingRequest.getProductSku()));
        }

        if ( StringHelper.isNotEmpty(pagingRequest.getName()) ) {
            specification = appendSpecification(specification, ProductSpecification.nameLike(pagingRequest.getName()));
        }

        if ( StringHelper.isNotEmpty(pagingRequest.getBrand()) ) {
            specification = appendSpecification(specification, ProductSpecification.brandLike(pagingRequest.getBrand()));
        }

        if ( StringHelper.isNotEmpty(pagingRequest.getColor()) ) {
            specification = appendSpecification(specification, ProductSpecification.colorLike(pagingRequest.getColor()));
        }

        if ( Objects.nonNull(pagingRequest.getPrice()) ) {
            specification = appendSpecification(specification, ProductSpecification.priceGreaterThan(pagingRequest.getPrice()));
        }

        return specification;
    }

    public ProductDTO createProduct (ProductRequestModel model) {
        //Validation
        Objects.requireNonNull(model, "ProductRequestModel is null");

        if ( StringHelper.isEmpty(model.getName()) ) {
            throw new IllegalArgumentException("'name' attribute must contain value");
        }

        String sku = model.getProductSku();
        if ( StringHelper.isEmpty(sku) ) {
            sku = SkuGenerator.generate(model.getName());
        }

        Product product = createProduct(sku, model.getName(), model.getDescription(), model.getBrand(), model.getColor(), model.getPrice());

        return product.getId().intValue() > 0 ? ProductMapper.INSTANCE.toDTO(product) : null;
    }

    public Product createProduct (String sku, String name, String description, String brand, String color, double price) {
        Product product = getProductBySku(sku);
        if ( product == null ) {
            product = new Product();
            ProductPrice productPrice = new ProductPrice();
            productPrice.setModifiedDate(Instant.now());
            productPrice.setProduct(product);
            productPrice.setPrice(price);
            product.setSku(sku);
            product.setName(name);
            product.setDescription(description);
            product.setBrand(brand);
            product.setColor(color);
            product.setPrices(Arrays.asList(productPrice));
            product.setCreatedDate(Instant.now());
            product.setModifiedDate(Instant.now());
            product.setActive(true);

            return productRepository.save(product);
        }

        return product;
    }

    public boolean updateProduct (ProductRequestModel model) throws ICommerceException {
        //Validation
        Objects.requireNonNull(model, "ProductRequestModel is null");

        if ( StringHelper.isEmpty(model.getProductSku()) ) {
            throw new IllegalArgumentException("'sku' attribute must contain value");
        }

        if ( !productRepository.existsBySku(model.getProductSku()) ) {
            throw new ICommerceException("Product with SKU: '" + model.getProductSku() + "' doesnot exist in Database");
        }

        Product product = getProductBySku(model.getProductSku());
        product.setName(model.getName());
        product.setDescription(model.getDescription());
        product.setBrand(model.getBrand());
        product.setColor(model.getColor());
        product.setModifiedDate(Instant.now());

        ProductPrice productPrice = new ProductPrice();
        productPrice.setPrice(model.getPrice());
        productPrice.setModifiedDate(Instant.now());
        product.addProductPrice(productPrice);

        product = productRepository.save(product);

        return product.getName().equals(model.getName());
    }

    public Product getProductBySku (String sku) {
        if ( StringHelper.isEmpty(sku) ) {
            throw new IllegalArgumentException("'productSku' attribute must contain value");
        }

        return productRepository.findBySku(sku).orElse(null);
    }

    public ProductDTO getProductDTOBySku (String sku) {
        if ( StringHelper.isEmpty(sku) ) {
            throw new IllegalArgumentException("'productSku' attribute must contain value");
        }

        return productRepository.findBySku(sku).map(ProductMapper.INSTANCE::toDTO).orElse(null);
    }

    public boolean deleteProductBySku (@NotEmpty String productSku) {
        if ( StringHelper.isEmpty(productSku) ) {
            throw new IllegalArgumentException("'productSku' attribute must contain value");
        }

        Product product = this.getProductBySku(productSku);
        if ( product != null ) {
            List<ProductPrice> productPrices = product.getPrices();
            productPriceRepository.deleteAll(productPrices);
            productRepository.delete(product);
        }

        return Objects.isNull(this.getProductBySku(productSku));
    }
}
