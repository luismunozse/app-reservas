package com.backend.app_reservas.service;

import com.backend.app_reservas.dto.ReservationRequestDTO;
import com.backend.app_reservas.exception.NoCapacityException;
import com.backend.app_reservas.exception.ResourceNotFoundException;
import com.backend.app_reservas.model.Availability;
import com.backend.app_reservas.model.Reservation;
import com.backend.app_reservas.model.ReservationStatus;
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
        // Validar que el usuario existe
        User client = userRepository.findById(request.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + request.getClientId()));
        // Validar que la fecha de reserva esté disponible
        Availability availability = availabilityRepository.findByAvailableDate(request.getReservationDate())
                .orElseThrow(() -> new ResourceNotFoundException("Fecha no disponible para reservas: " + request.getReservationDate()));

        if(!availability.isAvailable()) {
            throw new NoCapacityException("La fecha seleccionada no tiene disponibilidad: " + request.getReservationDate());
        }
        // Validar capacidad de visitantes
        Long approvedReservations = reservationRepository.countByReservationDateAndStatus(request.getReservationDate(), ReservationStatus.CONFIRMED);
        if (approvedReservations >= availability.getCapacity()) {
            throw new NoCapacityException("No hay capacidad disponible para la fecha: " + request.getReservationDate());
        }

        //Crear y guardar la reserva
        Reservation reservation = new Reservation();
        reservation.setClient(client);
        reservation.setReservationDate(request.getReservationDate());
        reservation.setStatus(ReservationStatus.PENDING); //Siempre inicia en estado Pendiente de confirmación

        return reservationRepository.save(reservation);
    }

    // Método para aprobar una reserva (Administrador)
    @Transactional
    public Reservation approveReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva " + reservationId + " no encontrada"));

        reservation.setStatus(ReservationStatus.CONFIRMED);
        return reservationRepository.save(reservation);
    }

}
