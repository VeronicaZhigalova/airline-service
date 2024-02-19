package com.awesomeorg.airlineservice.service_tests;

import com.awesomeorg.airlineservice.entity.Reservation;
import com.awesomeorg.airlineservice.exceptions.ReservationNotFoundException;
import com.awesomeorg.airlineservice.protocol.ReservationQuery;
import com.awesomeorg.airlineservice.protocol.UpdateReservationRequest;
import com.awesomeorg.airlineservice.repository.ReservationRepository;
import com.awesomeorg.airlineservice.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReservationServiceTest {
    @Mock
    private ReservationRepository reservationRepository;


    @InjectMocks
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deleteReservation() {
        Long reservationId = 1L;

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(new Reservation()));

        assertDoesNotThrow(() -> reservationService.deleteReservation(reservationId));

        verify(reservationRepository, times(1)).findById(reservationId);
        verify(reservationRepository, times(1)).deleteById(reservationId);
    }

    @Test
    void deleteReservation_ReservationNotFoundException() {
        Long nonExistingReservationId = 2L;

        when(reservationRepository.findById(nonExistingReservationId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            reservationService.deleteReservation(nonExistingReservationId);
        });

        assertEquals("Reservation not found with ID: " + nonExistingReservationId, exception.getReason());

        verify(reservationRepository, times(1)).findById(nonExistingReservationId);
        verify(reservationRepository, never()).deleteById(nonExistingReservationId);
    }

    @Test
    void findReservations() {
        ReservationQuery query = new ReservationQuery(
                "ABC123",
                "JFK",
                "LAX",
                LocalDateTime.parse("2023-04-15T08:00:00"),
                LocalDateTime.parse("2023-04-15T12:00:00"),
                "One-way",
                "New York",
                "Los Angeles",
                2,
                Reservation.FlightClass.BUSINESS,
                LocalDate.parse("2023-04-15"),
                null,
                Reservation.Status.CONFIRMED,
                1L,
                1L
        );
        Pageable pageable = Pageable.unpaged();

        when(reservationRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(new ArrayList<>()));

        Page<Reservation> result = reservationService.findReservations(query, pageable);

        verify(reservationRepository, times(1))
                .findAll(any(Specification.class), any(Pageable.class));

        assertNotNull(result);
    }




    @Test
    void findReservation_Success() {
        Long reservationId = 1L;

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(new Reservation()));

        Optional<Reservation> result = reservationService.findReservation(reservationId);

        verify(reservationRepository, times(1)).findById(reservationId);

        assertNotNull(result);
    }

    @Test
    void updateReservation() {
        Long reservationId = 1L;

        UpdateReservationRequest request = new UpdateReservationRequest();
        request.setFlightNumber("123");

        Long passengerId = 1L;

        Optional<Reservation> optionalReservation = Optional.of(new Reservation());

        when(reservationRepository.findById(reservationId)).thenReturn(optionalReservation);
        when(reservationRepository.save(any())).thenReturn(new Reservation());

        Reservation result = reservationService.updateReservation(reservationId, request, passengerId);

        verify(reservationRepository, times(1)).findById(reservationId);
        verify(reservationRepository, times(1)).save(any());

        assertNotNull(result);
    }


    @Test
    void updateReservation_ReservationNotFoundException() {
        Long nonExistingReservationId = 2L;
        UpdateReservationRequest request = new UpdateReservationRequest();

        when(reservationRepository.findById(nonExistingReservationId)).thenReturn(Optional.empty());

        ReservationNotFoundException exception = assertThrows(ReservationNotFoundException.class, () -> {
            reservationService.updateReservation(nonExistingReservationId, request, 1L);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Reservation not found with ID: " + nonExistingReservationId, exception.getReason());
    }
}