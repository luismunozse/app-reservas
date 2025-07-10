package com.backend.app_reservas.service;

import com.backend.app_reservas.model.User;
import com.backend.app_reservas.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario " + username + " no encontrado"));
        return new org.springframework.security.core.userdetails.User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(userEntity.getRole().name()))
        );
    }
}
