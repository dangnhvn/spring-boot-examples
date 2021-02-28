package com.example.icommerce.mappers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.icommerce.dtos.OrderDetailDTO;
import com.example.icommerce.entities.OrderDetail;

public class OrderDetailMapperDecorator implements OrderDetailMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderMapperDecorator.class);

    private final OrderDetailMapper delegate;

    public OrderDetailMapperDecorator (OrderDetailMapper delegate) {
        this.delegate = delegate;
    }


    public OrderDetailDTO toDTO (OrderDetail orderDetail) {
        if ( orderDetail == null ) {
            return null;
        }

        OrderDetailDTO dto = delegate.toDTO(orderDetail);
        dto.setProduct(ProductMapper.INSTANCE.toDTO(orderDetail.getProduct()));

        return dto;
    }
}
