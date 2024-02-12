package com.awesomeorg.airlineservice.service;

import com.awesomeorg.airlineservice.entity.Ticket;
import com.awesomeorg.airlineservice.exceptions.TicketAlreadyExistsException;
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
        return ticketRepository.findFreeTicket(dateOfFlight, pageRequest);
    }



    public List<Ticket> createTickets(CreateTicketRequest request) {
        LocalDate dateOfPurchase = LocalDate.now();

        final Page<Ticket> existingTickets = ticketRepository.findFreeTicket(dateOfPurchase, PageRequest.of(0, request.getSeat()));

        if (existingTickets.getContent().size() >= request.getSeat()) {
            throw new TicketAlreadyExistsException("Tickets already exist at this date");
        }

        List<Ticket> newTickets = new ArrayList<>();

        for (int i = 0; i < request.getSeat(); i++) {
            Ticket ticket = new Ticket();
            ticket.setDateOfFlight(request.getDateOfFlight());
            ticket.setDateOfPurchase(request.getDateOfPurchase());
            ticket.setDateOfReturn(request.getDateOfReturn());
            ticket.setSeat(request.getSeat());
            ticket.setPriceOfTicket(request.getPriceOfTicket());

            newTickets.add(ticket);

    }
        return ticketRepository.saveAll(newTickets);
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