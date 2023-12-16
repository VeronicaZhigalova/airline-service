package com.awesomeorg.airlineservice.repository;

import com.awesomeorg.airlineservice.entity.BlocklistedCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlocklistRepository extends JpaRepository<BlocklistedCustomer, Long> {
}
