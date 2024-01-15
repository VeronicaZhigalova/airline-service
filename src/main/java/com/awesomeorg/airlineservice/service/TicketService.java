package com.awesomeorg.airlineservice.service;

import com.awesomeorg.airlineservice.entity.Ticket;
import com.awesomeorg.airlineservice.protocol.TicketQuery;
import com.awesomeorg.airlineservice.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    public Optional<Ticket> findTicketById(final Long ticketId) {
        return ticketRepository.findById(ticketId);
    }

    public Page<Ticket> findFreeTicket(TicketQuery query, PageRequest pageRequest) {
        LocalDate dateOfFlight = LocalDate.ofEpochDay(query.getDateOfFlight());
        return ticketRepository.findFreeTicket(dateOfFlight, pageRequest);
    }
}