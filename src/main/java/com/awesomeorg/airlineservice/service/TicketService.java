package com.awesomeorg.airlineservice.service;

import com.awesomeorg.airlineservice.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
}
