package com.awesomeorg.airlineservice.service;

import com.awesomeorg.airlineservice.entity.Ticket;
import com.awesomeorg.airlineservice.exceptions.TicketAlreadyExistsException;
import com.awesomeorg.airlineservice.exceptions.TicketNotFoundException;
import com.awesomeorg.airlineservice.protocol.TicketQuery;
import com.awesomeorg.airlineservice.protocol.UpdateTicketRequest;
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
        if (ticketId == null) {
            return Optional.empty();
        }
        return ticketRepository.findById(ticketId);
    }

    public Page<Ticket> findFreeTicket(TicketQuery query, PageRequest pageRequest) {
        LocalDate dateOfFlight = query.getDateOfFlight();
        return ticketRepository.findFreeTicket(dateOfFlight, pageRequest);
    }



    public Ticket createTicket(TicketQuery request) {
        LocalDate dateOfPurchase = request.getDateOfPurchase();

        // Check if ticket already exists
        final Page<Ticket> existingTickets = ticketRepository.findFreeTicket(dateOfPurchase, PageRequest.of(0, 25));
        if (!existingTickets.isEmpty()) {
            throw new TicketAlreadyExistsException("Ticket already exists at this date");
        }

        // If not exists, create and save the new ticket
        final Ticket ticket = new Ticket();
        ticket.setDateOfFlight(request.getDateOfFlight());
        return ticketRepository.save(ticket);
    }




    public Ticket updateTicket(Long ticketId, UpdateTicketRequest request) {
        Optional<Ticket> optionalTicket = findTicketById(ticketId);
        if (optionalTicket.isPresent()) {
            Ticket ticket = optionalTicket.get();
            ticket.setDateOfPurchase(request.getDateOfPurchase());
            ticket.setDateOfFlight(request.getDateOfFlight());
            ticket.setDateOfReturn(request.getDateOfReturn());
            ticket.setSeat(request.getSeat());
            ticket.setPriceOfTicket(request.getPriceOfTicket());
            return ticketRepository.save(ticket);
        } else {
            throw new TicketNotFoundException("Ticket not found with ID: " + ticketId);
        }
    }

    public void deleteTicket(Long ticketId) {
        // Check if ticket with the given ticketId exists
       Optional<Ticket> ticket = findTicketById(ticketId);

        // If exists, delete the ticket
        if (ticket.isPresent()) {
            ticketRepository.deleteById(ticketId);
        } else {
            throw new TicketNotFoundException("Ticket not found with ID: " + ticketId);
        }
    }
}