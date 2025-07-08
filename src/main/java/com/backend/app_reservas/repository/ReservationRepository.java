package com.backend.app_reservas.repository;

import com.backend.app_reservas.model.Reservation;
import com.backend.app_reservas.model.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Long countByReservationDateAndStatus(LocalDate reservationDate, ReservationStatus status);

    // ...
    @Query("SELECT r.reservationDate, COUNT(r) FROM Reservation r WHERE r.status = 'APPROVED' AND r.reservationDate BETWEEN ?1 AND ?2 GROUP BY r.reservationDate")
    List<Object[]> countApprovedReservationsForMonth(LocalDate startDate, LocalDate endDate);
}
