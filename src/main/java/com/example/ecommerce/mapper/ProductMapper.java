package com.example.ecommerce.mapper;

import com.example.ecommerce.entity.Product;
import com.example.ecommerce.model.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "userId", target = "user.id")
    Product toEntity(ProductDto dto);

    @Mapping(source = "user.id", target = "userId")
    ProductDto toDto(Product entity);
}
