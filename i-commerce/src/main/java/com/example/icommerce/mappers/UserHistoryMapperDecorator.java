package com.example.icommerce.mappers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.icommerce.dtos.UserActivityDTO;
import com.example.icommerce.entities.UserActivity;

public class UserHistoryMapperDecorator implements UserHistoryMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserHistoryMapperDecorator.class);

    private final UserHistoryMapper delegate;

    public UserHistoryMapperDecorator (UserHistoryMapper delegate) {
        this.delegate = delegate;
    }

    @Override
    public UserActivityDTO toDTO (UserActivity entity) {
        UserActivityDTO dto = delegate.toDTO(entity);

        return dto;
    }
}
