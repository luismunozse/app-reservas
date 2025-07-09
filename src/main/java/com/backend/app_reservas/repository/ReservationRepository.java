package com.backend.app_reservas.repository;

import com.backend.app_reservas.model.Reservation;
import com.backend.app_reservas.model.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Long countByReservationDateAndStatus(LocalDate reservationDate, ReservationStatus status);

    @Query("SELECT r.reservationDate, COUNT(r) FROM Reservation r WHERE r.status = :status AND r.reservationDate BETWEEN :startDate AND :endDate GROUP BY r.reservationDate")
    List<Object[]> countReservationsByStatusForMonth(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("status") ReservationStatus status
    );
}
