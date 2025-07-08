package com.backend.app_reservas.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ReservationRequestDTO {

    private Long clientId;
    private LocalDate reservationDate;
}
