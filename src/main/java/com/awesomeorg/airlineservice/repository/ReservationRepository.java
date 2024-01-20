package com.awesomeorg.airlineservice.repository;

import com.awesomeorg.airlineservice.entity.Baggage;
import com.awesomeorg.airlineservice.entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>, PagingAndSortingRepository<Reservation, Long>,
        JpaSpecificationExecutor<Reservation> {


    @Query("SELECT r FROM reservations r " +
            "WHERE r.departure = :departure " +
            "AND r.destination = :destination " +
            "AND r.departureDate = :departureDate " +
            "AND r.fk_seat_id IS NULL")
    List<Reservation> findReservationsByDepartureDestinationAndDate(@Param("departure") String departure,
                                                                    @Param("destination") String destination,
                                                                    @Param("departureDate") LocalDate departureDate);
}
