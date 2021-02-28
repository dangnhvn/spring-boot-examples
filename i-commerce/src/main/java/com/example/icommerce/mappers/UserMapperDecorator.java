package com.example.icommerce.mappers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.icommerce.dtos.UserDTO;
import com.example.icommerce.entities.User;

public class UserMapperDecorator implements UserMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserHistoryMapperDecorator.class);

    private final UserMapper delegate;

    public UserMapperDecorator (UserMapper delegate) {
        this.delegate = delegate;
    }

    @Override
    public UserDTO toDTO (User entity) {
        return delegate.toDTO(entity);
    }
}
