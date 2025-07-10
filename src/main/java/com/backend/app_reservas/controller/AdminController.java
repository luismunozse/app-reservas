package com.backend.app_reservas.controller;

import com.backend.app_reservas.dto.ReservationResponseDTO;
import com.backend.app_reservas.mapper.ReservationMapper;
import com.backend.app_reservas.model.Reservation;
import com.backend.app_reservas.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    private final ReservationService reservationService;
    private final ReservationMapper reservationMapper;

    public AdminController(ReservationService reservationService, ReservationMapper reservationMapper) {
        this.reservationService = reservationService;
        this.reservationMapper = reservationMapper;
    }

    @GetMapping("/reservations/pending")
    public ResponseEntity<List<ReservationResponseDTO>> getPendingReservations() {
        List<Reservation> pendingReservations = reservationService.getPendingReservations();
        return ResponseEntity.ok(reservationMapper.toDtoList(pendingReservations));
    }

    @PostMapping("/reservations/{id}/approve")
    public ResponseEntity<ReservationResponseDTO> approveReservation(@PathVariable Long id) {
        Reservation approvedReservation = reservationService.approveReservation(id);
        return ResponseEntity.ok(reservationMapper.toDto(approvedReservation));
    }

    @PostMapping("/reservations/{id}/reject")
    public ResponseEntity<ReservationResponseDTO> rejectReservation(@PathVariable Long id) {
        Reservation reservation = reservationService.rejectReservation(id);
        return ResponseEntity.ok(reservationMapper.toDto(reservation));
    }
}
