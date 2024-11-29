package com.example.ecommerce.service.impl;

import com.example.ecommerce.entity.User;
import com.example.ecommerce.enums.Role;
import com.example.ecommerce.mapper.UserMapper;
import com.example.ecommerce.model.UserDto;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.UserService;
import com.example.ecommerce.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;
    private final JwtUtil util;

    @Override
    public UserDto register(UserDto dto) {
        if (repository.existsByUsername(dto.getUsername()) || repository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("username or email address already in use");
        }
        User user =mapper.toEntity(dto);
        user.setPassword(encoder.encode(dto.getPassword()));
        user.setRole(Role.CUSTOMER);
        User savedUser = repository.save(user);
        return mapper.toDto(savedUser);
    }

    @Override
    public String login(String username, String password) {
        User user = repository.findByUsername(username);
        if (user == null || !encoder.matches(password, user.getPassword())) {
            throw new RuntimeException("username or password incorrect");
        }
        return util.generateToken(username);
    }
}
