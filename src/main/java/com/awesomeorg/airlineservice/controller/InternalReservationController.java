package com.awesomeorg.airlineservice.controller;

import com.awesomeorg.airlineservice.entity.Reservation;
import com.awesomeorg.airlineservice.protocol.CreateReservationRequest;
import com.awesomeorg.airlineservice.protocol.UpdateReservationRequest;
import com.awesomeorg.airlineservice.service.ReservationService;
import com.awesomeorg.airlineservice.util.HeaderConstants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/internal/reservations")
@RequiredArgsConstructor
public class InternalReservationController {

    private final ReservationService reservationService;


    @PostMapping("/create")
    public ResponseEntity<Reservation> createReservation(@Valid @RequestBody CreateReservationRequest request,
                                                         @RequestHeader(HeaderConstants.PASSENGER_ID_HEADER) Long passengerId) {
        Reservation reservation = reservationService.createReservation(request, passengerId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reservation);
    }

    @GetMapping("/{reservationId})")
    public ResponseEntity<Reservation> findReservation(@PathVariable Long reservationId) {
        Optional<Reservation> reservation = reservationService.findReservation(reservationId);
        return reservation.map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PutMapping("{reservationId}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long reservationId,
                                                         @Valid @RequestBody UpdateReservationRequest request,
                                                         @PathVariable Long passengerId) {
        Reservation updatedReservation = reservationService.updateReservation(reservationId, request, passengerId);
        return ResponseEntity
                .ok()
                .body(updatedReservation);
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long reservationId) {
        reservationService.cancelReservation(reservationId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping()
    public ResponseEntity<List<Reservation>> searchReservations(@RequestParam(required = false) String departure,
                                                                @RequestParam(required = false) String destination,
                                                                @RequestParam(required = false) LocalDate departureDate) {
        List<Reservation> reservations = reservationService.findReservation(departure, destination, departureDate);
        return ResponseEntity.ok().body(reservations);
    }

}
