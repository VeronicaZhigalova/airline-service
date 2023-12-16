package com.awesomeorg.airlineservice.repository;

import com.awesomeorg.airlineservice.entity.Baggages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaggagesRepository extends JpaRepository<Baggages, Long> {
}
