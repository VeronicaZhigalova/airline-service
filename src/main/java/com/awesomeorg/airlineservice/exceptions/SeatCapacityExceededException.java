package com.awesomeorg.airlineservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class SeatCapacityExceededException extends ResponseStatusException {

    public SeatCapacityExceededException(final String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
