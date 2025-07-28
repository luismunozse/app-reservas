package com.backend.app_reservas.service;

import com.backend.app_reservas.dto.UserRegistrationDTO;
import com.backend.app_reservas.model.User;
import com.backend.app_reservas.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private  final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario: " + id + " no encontrado"));
    }

    public User registerUser(UserRegistrationDTO registrationDto) {
        // Verificar si el usuario ya existe
        if(userRepository.findByUsername(registrationDto.getUsername()).isPresent()) {
            throw new IllegalStateException("El nombre de usuario ya está en uso");
        }
        if(userRepository.findByEmail(registrationDto.getEmail()).isPresent()) {
            throw new IllegalStateException("El correo electrónico ya está en uso");
        }

        User user = new User();
        user.setUsername(registrationDto.getUsername());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setRole(registrationDto.getRole());

        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario: " + username + " no encontrado"));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Usuario: " + id + " no encontrado, no se puede eliminar.");
        }
        userRepository.deleteById(id);
    }

}
