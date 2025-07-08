package com.backend.app_reservas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class AvailabilityDTO {

    private LocalDate availableDate;
    private Long totalSlots;
    private Long availableSlots;


}
