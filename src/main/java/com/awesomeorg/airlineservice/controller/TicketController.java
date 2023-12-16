package com.awesomeorg.airlineservice.controller;

import com.awesomeorg.airlineservice.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;
}
