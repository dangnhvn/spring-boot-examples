package com.example.icommerce.mappers;


import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.icommerce.dtos.UserActivityDTO;
import com.example.icommerce.entities.UserActivity;

@Mapper
@DecoratedWith(UserHistoryMapperDecorator.class)
public interface UserHistoryMapper {

    UserHistoryMapper INSTANCE = Mappers.getMapper(UserHistoryMapper.class);

    UserActivityDTO toDTO (UserActivity entity);
}
