package com.awesomeorg.airlineservice.repository;

import com.awesomeorg.airlineservice.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
