package com.awesomeorg.airlineservice.controller;

import com.awesomeorg.airlineservice.entity.Reservation;
import com.awesomeorg.airlineservice.exceptions.BadRequestException;
import com.awesomeorg.airlineservice.exceptions.NotFoundException;
import com.awesomeorg.airlineservice.protocol.CreateReservationRequest;
import com.awesomeorg.airlineservice.service.ReservationService;
import com.awesomeorg.airlineservice.util.HeaderConstants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/create")
    public ResponseEntity<Reservation> createReservation(@Valid @RequestBody final CreateReservationRequest request,
                                                         @RequestHeader(HeaderConstants.PASSENGER_ID_HEADER) Long passengerId) {
            final Reservation reservation = reservationService.createReservation(request, passengerId);
            return status(HttpStatus.CREATED)
                    .body(reservation);

    }


    @DeleteMapping("/cancel/{reservationId}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long reservationId) {
        reservationService.cancelReservation(reservationId);
        return noContent()
                .build();
    }
}
