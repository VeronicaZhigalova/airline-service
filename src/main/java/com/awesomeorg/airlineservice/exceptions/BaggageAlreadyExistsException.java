package com.awesomeorg.airlineservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BaggageAlreadyExistsException extends ResponseStatusException{
        public BaggageAlreadyExistsException(final String message) {
            super(HttpStatus.BAD_REQUEST, message);
        }
    }
