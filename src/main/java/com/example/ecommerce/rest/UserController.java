package com.example.ecommerce.rest;

import com.example.ecommerce.model.UserDto;
import com.example.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto dto) {
        try {
            service.register(dto);
            return ResponseEntity.ok().body("User registered successfully");
        } catch (RuntimeException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto dto) {
        try {
            String token = service.login(dto.getUsername(), dto.getPassword());
            return ResponseEntity.status(HttpStatus.OK).body("Successfully logged in");
        } catch (RuntimeException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Authentication failed.");
        }
    }
}
