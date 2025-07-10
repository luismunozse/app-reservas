package com.backend.app_reservas.mapper;

import com.backend.app_reservas.dto.UserDTO;
import com.backend.app_reservas.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(User user);
    List<UserDTO> toDtoList(List<User> users);
}
