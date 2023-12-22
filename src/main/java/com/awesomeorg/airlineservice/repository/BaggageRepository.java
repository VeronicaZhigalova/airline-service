package com.awesomeorg.airlineservice.repository;

import com.awesomeorg.airlineservice.entity.Baggage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaggageRepository extends JpaRepository<Baggage, Long> {
}
