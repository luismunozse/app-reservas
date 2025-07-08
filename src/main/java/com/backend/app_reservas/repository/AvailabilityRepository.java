package com.backend.app_reservas.repository;

import com.backend.app_reservas.model.Availability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

    Optional<Availability> findByAvailableDate(LocalDate availableDate);

    List<Availability> findAllByAvailableDateBetween(LocalDate startDate, LocalDate endDate);

}

