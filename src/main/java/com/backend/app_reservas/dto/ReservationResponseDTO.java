package com.backend.app_reservas.dto;

import com.backend.app_reservas.model.ReservationStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ReservationResponseDTO {

    private Long id;
    private LocalDate reservationDate;
    private ReservationStatus status;
    private UserDTO client;
}
