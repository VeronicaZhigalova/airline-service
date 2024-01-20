package com.awesomeorg.airlineservice.repository;

import com.awesomeorg.airlineservice.entity.Baggage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

@Repository
public interface BaggageRepository extends JpaRepository<Baggage, Long> {

    @Query("SELECT b FROM baggages b " +
            "LEFT JOIN reservations r ON b.id = r.baggage.id AND r.dateOfPurchase= :date " +
            "WHERE r.id IS NULL")
    List<Baggage> getBaggageByReservation(@Param("date") LocalDate reservationDate);
}
