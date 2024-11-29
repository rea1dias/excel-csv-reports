package com.example.ecommerce.service.impl;

import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.mapper.ProductMapper;
import com.example.ecommerce.model.ProductDto;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final UserRepository userRepository;
    private final ProductMapper mapper;

    @Override
    public ProductDto create(ProductDto dto) {
        if (dto.getUserId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User ID must not be null");
        }
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Product product = mapper.toEntity(dto);
        product.setUser(user);
        Product savedProduct = repository.save(product);
        return mapper.toDto(savedProduct);
    }

    @Override
    public ProductDto get(Long id, ProductDto dto) {
        Product product = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found with id: " + id));
        return mapper.toDto(product);
    }

    @Override
    public ProductDto update(Long id, ProductDto dto) {
        Product newProduct = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + id));
        Product updatedProduct = mapper.toEntity(dto);
        updatedProduct.setId(id);
        repository.save(updatedProduct);
        return mapper.toDto(updatedProduct);
    }

    @Override
    public void delete(Long id) {
        repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + id));
        repository.deleteById(id);
    }
}
