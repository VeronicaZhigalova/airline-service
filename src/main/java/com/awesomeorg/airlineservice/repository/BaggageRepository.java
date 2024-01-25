package com.awesomeorg.airlineservice.repository;

import com.awesomeorg.airlineservice.entity.Baggage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

@Repository
public interface BaggageRepository extends JpaRepository<Baggage, Long> {


    @Query("SELECT b FROM baggages b WHERE b.reservationId = :reservationId")
    List<Baggage> getBaggageByReservation(@Param("reservationId") Long reservationId);

    Optional<Baggage> findByReservationId(Long reservationId);
}