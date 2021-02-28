package com.example.icommerce.mappers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.icommerce.dtos.ProductDTO;
import com.example.icommerce.entities.Product;
import com.example.icommerce.entities.ProductPrice;

public class ProductMapperDecorator implements ProductMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductMapperDecorator.class);

    private final ProductMapper delegate;

    public ProductMapperDecorator (ProductMapper delegate) {
        this.delegate = delegate;
    }

    @Override
    public ProductDTO toDTO (Product entity) {
        if ( entity == null ) {
            return null;
        }

        ProductPrice latestPrice = entity.getPrices().stream().min((t0, t1) -> t1.getModifiedDate().compareTo(t0.getModifiedDate())).orElse(null);

        ProductDTO dto = delegate.toDTO(entity);
        dto.setModifiedDate(latestPrice != null ? latestPrice.getModifiedDate() : entity.getModifiedDate());
        dto.setPrice(latestPrice != null ? latestPrice.getPrice() : 0.0);

        return dto;
    }
}
