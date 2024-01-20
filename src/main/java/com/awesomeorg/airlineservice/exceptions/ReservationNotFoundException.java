package com.awesomeorg.airlineservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ReservationNotFoundException extends ResponseStatusException {

    public ReservationNotFoundException(final String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}