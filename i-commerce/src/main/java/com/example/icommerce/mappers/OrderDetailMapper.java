package com.example.icommerce.mappers;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.icommerce.dtos.OrderDetailDTO;
import com.example.icommerce.entities.OrderDetail;

@Mapper
@DecoratedWith(OrderDetailMapperDecorator.class)
public interface OrderDetailMapper {

    OrderDetailMapper INSTANCE = Mappers.getMapper(OrderDetailMapper.class);

    @Mapping(target = "product", ignore = true)
    OrderDetailDTO toDTO (OrderDetail orderDetail);
}
