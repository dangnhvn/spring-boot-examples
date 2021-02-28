package com.example.icommerce.mappers;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.icommerce.dtos.ProductDTO;
import com.example.icommerce.entities.Product;

@Mapper
@DecoratedWith(ProductMapperDecorator.class)
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "price", ignore = true)
    ProductDTO toDTO (Product entity);
}
