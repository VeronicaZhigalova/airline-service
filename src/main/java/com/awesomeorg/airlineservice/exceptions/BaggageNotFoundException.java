package com.awesomeorg.airlineservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BaggageNotFoundException extends ResponseStatusException {

    public BaggageNotFoundException(final String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
