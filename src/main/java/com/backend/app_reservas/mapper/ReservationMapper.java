package com.backend.app_reservas.mapper;

import com.backend.app_reservas.dto.ReservationResponseDTO;
import com.backend.app_reservas.model.Reservation;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ReservationMapper {

    // Convierte Reservation a ReservationResponseDTO
    ReservationResponseDTO toDto(Reservation reservation);
    // Convierte una lista de Reservation a una lista de ReservationResponseDTO
    List<ReservationResponseDTO> toDtoList(List<Reservation> reservations);
}
