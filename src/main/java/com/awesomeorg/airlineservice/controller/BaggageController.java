package com.awesomeorg.airlineservice.controller;

import com.awesomeorg.airlineservice.entity.Baggage;
import com.awesomeorg.airlineservice.protocol.CreateBaggageRequest;
import com.awesomeorg.airlineservice.service.BaggageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/baggages")
@RequiredArgsConstructor
public class BaggageController {

    private final BaggageService baggageService;

    @PostMapping()
    public ResponseEntity<Baggage> register(@Valid @RequestBody final CreateBaggageRequest request) {
            final Baggage created = baggageService.createBaggage(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(created);
    }

    @DeleteMapping("/{baggageId}")
    public ResponseEntity<Void> remove(@PathVariable Long baggageId) {
        baggageService.removeBaggage(baggageId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<List<Baggage>> listByReservation(@RequestParam Long reservationId) {
        List<Baggage> baggageList = baggageService.getBaggageByReservation(reservationId);
        return ResponseEntity.ok(baggageList);
    }


    @GetMapping("/{baggageId}")
    public ResponseEntity<Baggage> getById(@PathVariable Long baggageId) {
            Baggage baggage = baggageService.getBaggageById(baggageId);
            return ResponseEntity.ok(baggage);

    }
}