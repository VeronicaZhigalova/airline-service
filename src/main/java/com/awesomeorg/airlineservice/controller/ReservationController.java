package com.awesomeorg.airlineservice.controller;

import com.awesomeorg.airlineservice.entity.Reservation;
import com.awesomeorg.airlineservice.protocol.ReservationQuery;
import com.awesomeorg.airlineservice.service.ReservationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping()
    public ResponseEntity<Reservation> createReservation(@Valid @RequestBody final ReservationQuery request,
                                                         @RequestHeader("passenger-id") Long passengerId) {
        final Reservation reservation = reservationService.createReservation(request, passengerId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reservation);
    }


    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long reservationId) {
        reservationService.deleteReservation(reservationId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<List<Reservation>> searchReservations(@NotNull @Valid ReservationQuery query) {
        List<Reservation> reservations = reservationService.findReservation(query);
        return ResponseEntity.ok().body(reservations);
    }
}