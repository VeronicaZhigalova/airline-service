package com.awesomeorg.airlineservice.repository;

import com.awesomeorg.airlineservice.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, PagingAndSortingRepository<Reservation, Long>,
        JpaSpecificationExecutor<Reservation> {
}
