package com.backend.app_reservas.controller;

import com.backend.app_reservas.dto.ReservationRequestDTO;
import com.backend.app_reservas.dto.ReservationResponseDTO;
import com.backend.app_reservas.mapper.ReservationMapper;
import com.backend.app_reservas.model.Reservation;
import com.backend.app_reservas.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final ReservationMapper reservationMapper;

    public ReservationController(ReservationService reservationService, ReservationMapper reservationMapper) {
        this.reservationService = reservationService;
        this.reservationMapper = reservationMapper;
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDTO> createReservation(@RequestBody ReservationRequestDTO request) {
        // Call the service to create a reservation
        Reservation newReservation = reservationService.createReservation(request);

        // Return the response entity
        return new ResponseEntity<>(reservationMapper.toDto(newReservation), HttpStatus.CREATED);
    }


}
