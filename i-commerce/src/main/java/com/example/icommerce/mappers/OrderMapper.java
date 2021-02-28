package com.example.icommerce.mappers;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.icommerce.dtos.OrderDTO;
import com.example.icommerce.entities.Order;

@Mapper
@DecoratedWith(OrderMapperDecorator.class)
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "total", ignore = true)
    @Mapping(target = "details", ignore = true)
    OrderDTO toDTO (Order entity);
}
