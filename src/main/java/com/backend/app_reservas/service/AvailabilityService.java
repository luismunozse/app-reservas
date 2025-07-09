package com.backend.app_reservas.service;

import com.backend.app_reservas.dto.AvailabilityDTO;
import com.backend.app_reservas.model.Availability;
import com.backend.app_reservas.model.ReservationStatus;
import com.backend.app_reservas.repository.AvailabilityRepository;
import com.backend.app_reservas.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AvailabilityService {

    private final AvailabilityRepository availabilityRepository;
    private final ReservationRepository reservationRepository;

    public AvailabilityService(AvailabilityRepository availabilityRepository, ReservationRepository reservationRepository) {
        this.availabilityRepository = availabilityRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<AvailabilityDTO> getMonthlyAvailability(YearMonth month) {
        // Obtener todas las disponibilidades del mes
        List<Availability> monthRules = availabilityRepository.findAllByAvailableDateBetween(month.atDay(1), month.atEndOfMonth());
        // Contar las reservas confirmadas para cada día del mes
        Map<LocalDate, Long> approvedReservationsCount = reservationRepository
                .countReservationsByStatusForMonth(month.atDay(1), month.atEndOfMonth(), ReservationStatus.CONFIRMED)
                .stream()
                .collect(Collectors.toMap(
                        result -> (LocalDate) result[0],
                        result -> (Long) result[1]
                ));

        List<AvailabilityDTO> availabilityDtos = new ArrayList<>();

        for (Availability dayRule : monthRules) {
            // Verificar si el día tiene disponibilidad
            if (dayRule.isAvailable()) {
                // Calcular la capacidad total y los cupos disponibles
                long totalCapacity = dayRule.getCapacity();
                long occupiedSlots = approvedReservationsCount.getOrDefault(dayRule.getAvailableDate(), 0L);
                long availableSlots = totalCapacity - occupiedSlots;
                // Crear el DTO de disponibilidad para el día
                availabilityDtos.add(
                        new AvailabilityDTO(
                                dayRule.getAvailableDate(), // Fecha de disponibilidad
                                totalCapacity, // Capacidad total del día
                                Math.max(0, availableSlots) // Asegurarse de que no sea negativo el número de cupos disponibles
                        )
                );
            }

        }
        return availabilityDtos;
    }
}
