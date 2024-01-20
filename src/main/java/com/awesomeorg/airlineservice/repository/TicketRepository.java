package com.awesomeorg.airlineservice.repository;

import com.awesomeorg.airlineservice.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query("SELECT t FROM tickets t WHERE t.dateOfPurchase = :dateOfPurchase")
    Page<Ticket> findFreeTicket(@Param("dateOfPurchase") LocalDate dateOfPurchase,PageRequest pageRequest);

}
