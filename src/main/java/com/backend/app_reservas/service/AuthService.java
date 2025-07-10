package com.backend.app_reservas.service;

import com.backend.app_reservas.dto.AuthRequestDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    public String login(AuthRequestDTO request) {
        // Authenticate the user using the authentication manager
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            )
        );

        // Load user details
       final UserDetails user = userDetailsService.loadUserByUsername(request.getUsername());

        // Generate JWT token
        return jwtService.generateToken(user);
    }
}
