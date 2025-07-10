package com.backend.app_reservas.controller;

import com.backend.app_reservas.dto.UserDTO;
import com.backend.app_reservas.dto.UserRegistrationDTO;
import com.backend.app_reservas.mapper.UserMapper;
import com.backend.app_reservas.model.User;
import com.backend.app_reservas.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserRegistrationDTO registrationDTO){
        User newUser = userService.registerUser(registrationDTO);
        return new ResponseEntity<>(userMapper.toDto(newUser),
                                    HttpStatus.CREATED);
    }

    @GetMapping("/my-profile")
    public ResponseEntity<UserDTO> getMyProfile(Authentication authentication) {
        String currentUsername = authentication.getName();
        User user = userService.findByUsername(currentUsername);

        // 1. Convertir la entidad User a UserDTO usando el mapper
        UserDTO userDto = userMapper.toDto(user);

        // 2. Devolver el DTO en la respuesta con estado OK (200)
        return ResponseEntity.ok(userDto);
    }
}
