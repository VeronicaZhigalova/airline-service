package com.awesomeorg.airlineservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TicketAlreadyExistsException extends ResponseStatusException {

    public TicketAlreadyExistsException(final String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}