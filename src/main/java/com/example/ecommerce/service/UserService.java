package com.example.ecommerce.service;

import com.example.ecommerce.model.UserDto;

public interface UserService {
    UserDto register(UserDto dto);
    String login(String username, String password);
}
