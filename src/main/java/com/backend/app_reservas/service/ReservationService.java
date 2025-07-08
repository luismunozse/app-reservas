package com.backend.app_reservas.service;

import com.backend.app_reservas.dto.ReservationRequestDTO;
import com.backend.app_reservas.exception.ResourceNotFoundException;
import com.backend.app_reservas.model.Availability;
import com.backend.app_reservas.model.Reservation;
import com.backend.app_reservas.model.User;
import com.backend.app_reservas.repository.AvailabilityRepository;
import com.backend.app_reservas.repository.ReservationRepository;
import com.backend.app_reservas.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final AvailabilityRepository availabilityRepository;
    private final UserRepository userRepository;

    public ReservationService(ReservationRepository reservationRepository,AvailabilityRepository availabilityRepository,UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.availabilityRepository = availabilityRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Reservation createReservation(ReservationRequestDTO request){
        User client = userRepository.findById(request.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + request.getClientId()));

        Availability availability = availabilityRepository.findByAvailableDate(request.getReservationDate())
                .orElseThrow(() -> new ResourceNotFoundException("Fecha no disponible para reservas: " + request.getReservationDate()));

        if (!availability.isAvailable()) {

        }
    }

    @Transactional
    public Reservation approveReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + reservationId));

        reservation.setStatus(ReservationStatus.APPROVED);
        return reservationRepository.save(reservation);
    }

}
