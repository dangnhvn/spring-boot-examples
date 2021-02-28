package com.example.icommerce.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.example.icommerce.dtos.ProductDTO;
import com.example.icommerce.entities.ProductPrice;
import com.example.icommerce.exceptions.ICommerceException;
import com.example.icommerce.models.ProductPagingRequest;
import com.example.icommerce.models.ProductRequestModel;

public class ProductServiceTest extends BaseServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    public void testCreateProduct () {
        ProductRequestModel model = new ProductRequestModel();
        model.setName("product name");
        model.setBrand("product brand");
        model.setColor("product color");
        model.setDescription("product description");
        model.setPrice(101028.0);

        ProductDTO dto = productService.createProduct(model);
        Assertions.assertNotNull(dto);
        Assertions.assertTrue(dto.getSku().startsWith("ICMPRO"));
        Assertions.assertEquals("product name", dto.getName());
        Assertions.assertEquals("product brand", dto.getBrand());
        Assertions.assertEquals("product color", dto.getColor());
        Assertions.assertEquals(101028.0, dto.getPrice());

        productService.deleteProductBySku(dto.getSku());
    }

    @Test
    public void testCreateProductWithNameIsEmpty () {
        ProductRequestModel model = new ProductRequestModel();
        model.setName("");
        model.setBrand("product brand");
        model.setColor("product color");
        model.setDescription("product description");
        model.setPrice(101028.0);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                                                                     () -> productService.createProduct(model));
        Assertions.assertEquals("'name' attribute must contain value", exception.getMessage());
    }

    @Test
    public void testCreateProductWithNullPointerException () {
        NullPointerException exception = Assertions.assertThrows(NullPointerException.class,
                                                                 () -> productService.createProduct(null));
        Assertions.assertEquals("ProductRequestModel is null", exception.getMessage());
    }

    @Test
    public void testCreateProductWithProductAlreadyExisting () {
        String sku = "ICMAIP000009";
        ProductRequestModel model = new ProductRequestModel();
        model.setProductSku(sku);
        model.setName("name");
        model.setBrand("product brand");
        model.setColor("product color");
        model.setDescription("product description");
        model.setPrice(101028.0);

        ProductDTO dto = productService.createProduct(model);
        Assertions.assertNotNull(dto);
    }

    @Test
    public void testUpdateProduct () throws ICommerceException {
        String sku = "ICMAIP000009";
        ProductRequestModel model = new ProductRequestModel();
        model.setProductSku(sku);
        model.setName("product name");
        model.setBrand("product brand");
        model.setColor("product color");
        model.setDescription("product description");
        model.setPrice(101028.0);

        boolean updateSuccess = productService.updateProduct(model);
        Assertions.assertTrue(updateSuccess);

        ProductDTO dto = productService.getProductDTOBySku(sku);
        Assertions.assertEquals("product name", dto.getName());
        Assertions.assertEquals("product brand", dto.getBrand());
        Assertions.assertEquals("product color", dto.getColor());
        Assertions.assertEquals(101028.0, dto.getPrice());
    }

    @Test
    public void testUpdateProductWithException () {
        String sku = "ICM000100020";
        ProductRequestModel model = new ProductRequestModel();
        model.setProductSku(sku);
        model.setName("product name");
        model.setBrand("product brand");
        model.setColor("product color");
        model.setDescription("product description");
        model.setPrice(101028.0);

        ICommerceException exception = Assertions.assertThrows(ICommerceException.class,
                                                               () -> productService.updateProduct(model));
        Assertions.assertEquals("Product with SKU: '" + sku + "' doesnot exist in Database", exception.getMessage());
    }

    @Test
    public void testGetProductsWithLimit () {
        ProductPagingRequest paging = new ProductPagingRequest();
        paging.setLimit(10);

        Page<ProductDTO> dtoPages = productService.getProducts(paging);
        Assertions.assertEquals(10, dtoPages.getTotalPages());
        Assertions.assertEquals(100, dtoPages.getTotalElements());
        Assertions.assertEquals(10, dtoPages.getContent().size());

    }

    @Test
    public void testGetProductsWithFilter () {
        ProductPagingRequest paging = new ProductPagingRequest();
        paging.setLimit(10);
        paging.setName("Product");
        paging.setColor("Color");
        paging.setBrand("Brand");
        paging.setPrice(0.0);

        Page<ProductDTO> dtoPages = productService.getProducts(paging);
        Assertions.assertEquals(10, dtoPages.getContent().size());
        Assertions.assertEquals(2, dtoPages.getTotalPages());

    }

    @Test
    public void testGetProductsWithFilterProductSku () {
        ProductPagingRequest paging = new ProductPagingRequest();
        paging.setProductSku("ICMAIP000009");

        Page<ProductDTO> dtoPages = productService.getProducts(paging);
        Assertions.assertEquals(1, dtoPages.getContent().size());
        Assertions.assertEquals(1, dtoPages.getTotalPages());

    }

    @Test
    public void testGetLatestPrice () throws ICommerceException {
        double price = 101028.0;
        String sku = "ICMAIP000009";
        testUpdateProduct();

        ProductPrice productPrice = productService.getLatestPrice(sku);
        Assertions.assertEquals(price, productPrice.getPrice());

    }

    @Test
    public void testGetProductDTOBySkuThrowException () {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                                                                     () -> productService.getProductDTOBySku(null));
        Assertions.assertEquals("'productSku' attribute must contain value", exception.getMessage());
    }

    @Test
    public void testGetProductBySkuThrowException () {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                                                                     () -> productService.getProductBySku(null));
            Assertions.assertEquals("'productSku' attribute must contain value", exception.getMessage());
    }

    @Test
    public void testDeleteProductWithIllegalArgumentException() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                                                                     () -> productService.deleteProductBySku(null));
        Assertions.assertEquals("'productSku' attribute must contain value", exception.getMessage());
    }

}
