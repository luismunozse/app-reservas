package com.backend.app_reservas.service;

import com.backend.app_reservas.dto.ReservationRequestDTO;
import com.backend.app_reservas.model.Availability;
import com.backend.app_reservas.model.Reservation;
import com.backend.app_reservas.model.ReservationStatus;
import com.backend.app_reservas.model.User;
import com.backend.app_reservas.repository.AvailabilityRepository;
import com.backend.app_reservas.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private AvailabilityRepository availabilityRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    void createReservation_ShouldCreateReservation_WhenValidRequest() {
        ReservationRequestDTO request = new ReservationRequestDTO();
        request.setClientId(1L);
        request.setReservationDate(LocalDate.now().plusDays(10));

        User mockUser = new User();
        mockUser.setId(1L);

        Availability mockAvailability = new Availability();
        mockAvailability.setAvailable(true);
        mockAvailability.setCapacity(10);

        when(UserService.findById(1L)).thenReturn(mockUser);
        when(availabilityRepository.findByAvailableDate(request.getReservationDate())).thenReturn(Optional.of(mockAvailability));
        when(reservationRepository.countByReservationDateAndStatus(request.getReservationDate(), ReservationStatus.CONFIRMED)).thenReturn(5L);
        when(reservationRepository.save(any(Reservation.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }
}
