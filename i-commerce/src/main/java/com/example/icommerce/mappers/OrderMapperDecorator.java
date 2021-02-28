package com.example.icommerce.mappers;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.icommerce.dtos.OrderDTO;
import com.example.icommerce.entities.Order;

public class OrderMapperDecorator implements OrderMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderMapperDecorator.class);

    private final OrderMapper delegate;

    public OrderMapperDecorator (OrderMapper delegate) {
        this.delegate = delegate;
    }

    @Override
    public OrderDTO toDTO (Order entity) {
        OrderDTO dto = delegate.toDTO(entity);
        dto.setTotal(BigDecimal.valueOf(entity.getTotalOrderPrice()));
        dto.setDetails(entity.getOrderDetailList().stream().map(OrderDetailMapper.INSTANCE::toDTO).filter(Objects::nonNull).collect(Collectors.toList()));

        return dto;
    }

}
