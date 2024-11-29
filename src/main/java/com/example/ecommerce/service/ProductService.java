package com.example.ecommerce.service;

import com.example.ecommerce.model.ProductDto;

public interface ProductService {
    ProductDto create(ProductDto dto);
    ProductDto get(Long id, ProductDto dto);
    ProductDto update(Long id,  ProductDto dto);
    void delete(Long id);
}
