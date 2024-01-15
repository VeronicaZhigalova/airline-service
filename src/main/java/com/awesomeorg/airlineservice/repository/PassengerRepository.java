package com.awesomeorg.airlineservice.repository;

import com.awesomeorg.airlineservice.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    @Query("SELECT p FROM passengers p WHERE p.emailAddress = :email")
    Optional<Passenger> findByEmail(@Param("email") String emailAddress);
}

