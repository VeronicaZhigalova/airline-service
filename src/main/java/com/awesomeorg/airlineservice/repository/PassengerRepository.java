package com.awesomeorg.airlineservice.repository;

import com.awesomeorg.airlineservice.entity.Passengers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends JpaRepository<Passengers, Long> {
}
