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

@RestController
@RequestMapping("/passengers")
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerService passengerService;

    @PostMapping("/register")
    public ResponseEntity<Passenger> registerPassenger(@Valid @RequestBody final CreatePassengerRequest request) {
        final Passenger created = passengerService.createPassenger(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(created);
    }

    @DeleteMapping("/delete/{passengerId}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long passengerId) {
        passengerService.deletePassenger(passengerId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{passengerId}")
    public ResponseEntity<Passenger> updatePassenger(@PathVariable Long passengerId,
                                                     @Valid @RequestBody UpdatePassengerRequest request) {
        Passenger passenger = passengerService.updatePassenger(passengerId, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(passenger);
    }
}
