package com.backend.app_reservas.dto;

import com.backend.app_reservas.model.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationDTO {

    private String username;
    private String email;
    private String password;
    private Role role;
}
