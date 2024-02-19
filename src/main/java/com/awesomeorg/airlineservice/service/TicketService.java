package com.awesomeorg.airlineservice.service;

import com.awesomeorg.airlineservice.entity.Ticket;
import com.awesomeorg.airlineservice.exceptions.TicketAvailabilityExceededException;
import com.awesomeorg.airlineservice.exceptions.TicketNotFoundException;
import com.awesomeorg.airlineservice.protocol.CreateTicketRequest;
import com.awesomeorg.airlineservice.protocol.UpdateTicketRequest;
import com.awesomeorg.airlineservice.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

    public Page<Ticket> findFreeTicket(CreateTicketRequest query, PageRequest pageRequest) {
        LocalDate dateOfFlight = query.getDateOfFlight();
        return ticketRepository.findFreeTicket(dateOfFlight,pageRequest);
    }



    public List<Ticket> createTickets(CreateTicketRequest request) {
        LocalDate dateOfPurchase = LocalDate.now();

        final Page<Ticket> existingTickets = ticketRepository.findFreeTicket(dateOfPurchase, PageRequest.of(0, Integer.MAX_VALUE));

        int availableSeats = calculateAvailableSeats(existingTickets.getContent().size(), 100);

        if (availableSeats < request.getSeat()) {
            throw new TicketAvailabilityExceededException("Not enough available seats for purchase");
        }

        List<Ticket> newTickets = new ArrayList<>();

        for (int i = 0; i < request.getSeat(); i++) {
            if (i >= availableSeats) {
                break;
            }

            Ticket ticket = new Ticket();
            ticket.setDateOfFlight(request.getDateOfFlight());
            ticket.setDateOfPurchase(dateOfPurchase);
            ticket.setDateOfReturn(request.getDateOfReturn());
            ticket.setSeat(existingTickets.getContent().size() + i + 1);
            ticket.setPriceOfTicket(request.getPriceOfTicket());

            newTickets.add(ticket);
        }

        List<Ticket> savedTickets = ticketRepository.saveAll(newTickets);

        return savedTickets;
    }

    private int calculateAvailableSeats(int existingTicketsCount, int totalSeatsLimit) {
        return Math.max(totalSeatsLimit - existingTicketsCount, 0);
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

       Optional<Ticket> ticket = findTicketById(ticketId);

        if (ticket.isPresent()) {
            ticketRepository.deleteById(ticketId);
        } else {
            throw new TicketNotFoundException("Ticket not found with ID: " + ticketId);
        }
    }
}