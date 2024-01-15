package com.awesomeorg.airlineservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CustomerAlreadyExistsException extends ResponseStatusException {
    public CustomerAlreadyExistsException(final String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
