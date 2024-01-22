package com.awesomeorg.airlineservice.controller;

import com.awesomeorg.airlineservice.entity.Ticket;
import com.awesomeorg.airlineservice.protocol.TicketQuery;
import com.awesomeorg.airlineservice.protocol.UpdateTicketRequest;
import com.awesomeorg.airlineservice.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {


    private final TicketService ticketService;

    @GetMapping("/find")
    public ResponseEntity<Page<Ticket>> findTicket(@Valid final TicketQuery query,
                                                   @RequestParam(defaultValue = "0", required = false) final int pageNumber,
                                                   @RequestParam(defaultValue = "25", required = false) final int pageSize) {

        final Page<Ticket> tickets = ticketService.findFreeTicket(query, PageRequest.of(pageNumber, pageSize));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tickets);

    }

    @PostMapping("/create")
    public ResponseEntity<Ticket> createTicket(@Valid @RequestBody final TicketQuery request) {
        final Ticket createdTicket = ticketService.createTicket(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdTicket);
    }

    @PutMapping("/update/{ticketId}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable Long ticketId,
                                               @Valid @RequestBody UpdateTicketRequest request) {
        if (ticketId == null) {
            throw new IllegalArgumentException("Ticket ID cannot be null");
        }
        Ticket updatedTicket = ticketService.updateTicket(ticketId, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(updatedTicket);
    }

    @DeleteMapping("/delete/{ticketId}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long ticketId) {
        ticketService.deleteTicket(ticketId);
        return ResponseEntity.noContent().build();
    }
}


