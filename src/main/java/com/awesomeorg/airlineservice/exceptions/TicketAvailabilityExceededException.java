package com.awesomeorg.airlineservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TicketAvailabilityExceededException extends ResponseStatusException {

    public TicketAvailabilityExceededException(final String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
