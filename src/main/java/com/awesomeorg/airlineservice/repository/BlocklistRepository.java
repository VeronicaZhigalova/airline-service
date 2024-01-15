package com.awesomeorg.airlineservice.repository;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import com.awesomeorg.airlineservice.entity.BlocklistedCustomer;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface BlocklistRepository extends JpaRepository<BlocklistedCustomer, Long> {

    Optional<BlocklistedCustomer> findBlocklistedCustomerByCustomerId(@Param("customerId") Long customerId);

}