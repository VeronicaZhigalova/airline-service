package com.awesomeorg.airlineservice.controller;

import com.awesomeorg.airlineservice.entity.Baggage;
import com.awesomeorg.airlineservice.protocol.CreateBaggageRequest;
import com.awesomeorg.airlineservice.service.BaggageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/baggages")
@RequiredArgsConstructor
public class BaggageController {

    private final BaggageService baggageService;

    @PostMapping("/register")
    public ResponseEntity<Baggage> register(@Valid @RequestBody final CreateBaggageRequest request) {
        final Baggage created = baggageService.createBaggage(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(created);
    }
}

