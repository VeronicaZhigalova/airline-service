package com.awesomeorg.airlineservice.controller;

import com.awesomeorg.airlineservice.entity.Passenger;
import com.awesomeorg.airlineservice.protocol.CreatePassengerRequest;
import com.awesomeorg.airlineservice.service.PassengerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/passengers")
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerService passengerService;

    @PostMapping("/register")
    public ResponseEntity<Passenger> register(@Valid @RequestBody final CreatePassengerRequest request) {
        final Passenger created = passengerService.createPassenger(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(created);
    }
}
