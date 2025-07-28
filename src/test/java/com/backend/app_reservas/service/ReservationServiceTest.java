package com.backend.app_reservas.service;

import com.backend.app_reservas.dto.ReservationRequestDTO;
import com.backend.app_reservas.exception.NoCapacityException;
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

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private AvailabilityRepository availabilityRepository;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    void createReservation_happyPath() {
        ReservationRequestDTO request = new ReservationRequestDTO();
        request.setVisitorName("Juan Perez");
        request.setVisitorEmail("juanperez@gmail.com");
        request.setReservationDate(LocalDate.now().plusDays(10));

        Availability mockAvailability = new Availability();
        mockAvailability.setAvailable(true);
        mockAvailability.setCapacity(10);

        // Simulamos el comportamiento del repositorio de usuarios
        when(availabilityRepository.findByAvailableDate(request.getReservationDate())).thenReturn(Optional.of(mockAvailability));
        when(reservationRepository.countByReservationDateAndStatus(request.getReservationDate(), ReservationStatus.CONFIRMED)).thenReturn(5L);

        //Simulamos el guardado de la reserva en la base de datos
        doAnswer(invocation -> invocation.getArgument(0)).when(reservationRepository).save(any(Reservation.class));

        // Llamamos al método que queremos probar
        Reservation result = reservationService.createReservation(request);

        // Verificamos que la reserva se haya creado correctamente
        assertNotNull(result);
        assertEquals(ReservationStatus.PENDING, result.getStatus());
        assertEquals(request.getVisitorName(), result.getVisitorName());
        assertEquals(request.getVisitorEmail(), result.getVisitorEmail());
        assertEquals(request.getReservationDate(), result.getReservationDate());

    }

    @Test
    void createReservation_WhenNoCapacity(){
        ReservationRequestDTO request = new ReservationRequestDTO();
        request.setVisitorName("Juan Perez");
        request.setVisitorEmail("juanperez@gmail.com");
        request.setReservationDate(LocalDate.now().plusDays(10));

        Availability mockAvailability = new Availability();
        mockAvailability.setAvailable(true);
        mockAvailability.setCapacity(10);

        when(availabilityRepository.findByAvailableDate(request.getReservationDate())).thenReturn(Optional.of(mockAvailability));
        when(reservationRepository.countByReservationDateAndStatus(request.getReservationDate(), ReservationStatus.CONFIRMED)).thenReturn(5L);

        assertThrows(NoCapacityException.class, () -> {
            reservationService.createReservation(request);
        });

    }
}
