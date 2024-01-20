package com.awesomeorg.airlineservice.controller;

import com.awesomeorg.airlineservice.entity.Passenger;
import com.awesomeorg.airlineservice.protocol.CreatePassengerRequest;
import com.awesomeorg.airlineservice.protocol.UpdatePassengerRequest;
import com.awesomeorg.airlineservice.service.PassengerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/internal/passengers")
@RequiredArgsConstructor
public class InternalPassengerController {

    private final PassengerService passengerService;

    @PostMapping("/create")
    public ResponseEntity<Passenger> createPassenger(@Valid @RequestBody CreatePassengerRequest request) {
        Passenger passenger = passengerService.createPassenger(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(passenger);
    }

    @GetMapping("/find/{passengerId}")
    public ResponseEntity<Passenger> findPassenger(@PathVariable Long passengerId) {
        Optional<Passenger> passenger = passengerService.findPassengerById(passengerId);
        return passenger.map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{passengerId}")
    public ResponseEntity<Passenger> updatePassenger(@PathVariable Long passengerId,
                                                     @Valid @RequestBody UpdatePassengerRequest request) {
        Passenger updatedPassenger = passengerService.updatePassenger(passengerId, request);
        return ResponseEntity.ok().body(updatedPassenger);
    }

    @DeleteMapping("/delete/{passengerId}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long passengerId) {
        passengerService.deletePassenger(passengerId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{passengerId}")
    public ResponseEntity<Optional<Passenger>> searchPassengers(@PathVariable Long passengerId) {
        Optional<Passenger> passengers = passengerService.findPassengerById(passengerId);
        return ResponseEntity.ok().body(passengers);
    }
}