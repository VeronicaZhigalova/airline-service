package com.awesomeorg.airlineservice.controller;

import com.awesomeorg.airlineservice.entity.Reservation;
import com.awesomeorg.airlineservice.exceptions.BadRequestException;
import com.awesomeorg.airlineservice.exceptions.NotFoundException;
import com.awesomeorg.airlineservice.exceptions.ReservationNotFoundException;
import com.awesomeorg.airlineservice.protocol.CreateReservationRequest;
import com.awesomeorg.airlineservice.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping()
    public ResponseEntity<Reservation> createReservation(@Valid @RequestBody final CreateReservationRequest request,
                                                         @RequestHeader("passenger-id") Long passengerId) {
        try {
            final Reservation reservation = reservationService.createReservation(request, passengerId);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(reservation);
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }


    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long reservationId) {
        try {
            reservationService.cancelReservation(reservationId);
            return ResponseEntity.noContent().build();
        } catch (ReservationNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

