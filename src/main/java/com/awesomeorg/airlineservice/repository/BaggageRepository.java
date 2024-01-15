package com.awesomeorg.airlineservice.repository;

import com.awesomeorg.airlineservice.entity.Baggage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import java.util.List;

@Repository
public interface BaggageRepository extends JpaRepository<Baggage, Long> {

    @Query("SELECT b FROM Baggage b " +
            "LEFT JOIN Reservation r ON b.id = r.ticketId AND r.reservationDate = :date " +
            "WHERE r.id IS NULL")
    List<Baggage> findFreeTicket(@Param("date") LocalDate reservationDate);

}
