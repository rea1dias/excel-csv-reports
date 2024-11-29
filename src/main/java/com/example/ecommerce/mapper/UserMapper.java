package com.example.ecommerce.mapper;

import com.example.ecommerce.entity.User;
import com.example.ecommerce.model.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "products", ignore = true)
    User toEntity(UserDto dto);

    UserDto toDto(User entity);
}
