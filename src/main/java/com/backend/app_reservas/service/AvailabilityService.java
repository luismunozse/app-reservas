package com.backend.app_reservas.service;

import com.backend.app_reservas.dto.AvailabilityDTO;
import com.backend.app_reservas.model.Availability;
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
        List<Availability> monthRules = availabilityRepository.findAllByAvailableDateBetween(month.atDay(1), month.atEndOfMonth());
        Map<LocalDate, Long> approvedReservationsCount = reservationRepository
                .countApprovedReservationsForMonth(month.atDay(1), month.atEndOfMonth())
                .stream()
                .collect(Collectors.toMap(
                        result -> (LocalDate) result[0],
                        result -> (Long) result[1]
                ));

        List<AvailabilityDTO> availabilityDtos = new ArrayList<>();

        for (Availability dayRule : monthRules) {
            if (dayRule.isAvailable()) {
                Long totalSlots = dayRule.getTotalSlots();
                Long occupiedSlots = approvedReservationsCount.getOrDefault(dayRule.getAvailableDate(), 0L);
                Long availableSlots = totalSlots - occupiedSlots;

                availabilityDtos.add(
                        new AvailabilityDTO(
                                dayRule.getAvailableDate(),
                                totalSlots,
                                availableSlots
                        )
                );
            }

        }
        return availabilityDtos;
    }
}
