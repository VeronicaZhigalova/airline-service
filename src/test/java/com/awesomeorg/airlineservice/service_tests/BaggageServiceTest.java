package com.awesomeorg.airlineservice.service_tests;


import com.awesomeorg.airlineservice.entity.Baggage;
import com.awesomeorg.airlineservice.exceptions.BaggageAlreadyExistsException;
import com.awesomeorg.airlineservice.exceptions.BaggageNotFoundException;
import com.awesomeorg.airlineservice.protocol.CreateBaggageRequest;
import com.awesomeorg.airlineservice.repository.BaggageRepository;
import com.awesomeorg.airlineservice.service.BaggageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BaggageServiceTest {

    @Mock
    private BaggageRepository baggageRepository;

    @InjectMocks
    private BaggageService baggageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createBaggage() {
        CreateBaggageRequest request = new CreateBaggageRequest();
        request.setReservationId(1L);

        when(baggageRepository.findByReservationId(any())).thenReturn(Optional.empty());
        when(baggageRepository.save(any())).thenReturn(new Baggage());

        Baggage createdBaggage = baggageService.createBaggage(request);

        verify(baggageRepository, times(1)).findByReservationId(any());
        verify(baggageRepository, times(1)).save(any());

        assertNotNull(createdBaggage);
    }

    @Test
    void createBaggage_BaggageAlreadyExistsException() {
        CreateBaggageRequest request = new CreateBaggageRequest();
        request.setReservationId(1L);

        when(baggageRepository.findByReservationId(request.getReservationId())).thenReturn(Optional.of(new Baggage()));

        BaggageAlreadyExistsException exception = assertThrows(BaggageAlreadyExistsException.class, () -> {
            baggageService.createBaggage(request);
        });

        assertTrue(exception.getMessage().contains("Baggage already exists"));

    }

    @Test
    void removeBaggage() {
        Long baggageId = 1L;

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                baggageService.removeBaggage(baggageId));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Baggage not found with id: " + baggageId, exception.getReason());

        verify(baggageRepository, never()).deleteById(eq(baggageId));
    }



    @Test
    void removeBaggage_BaggageNotFoundException() {
        long nonExistingBaggageId = 2L;
        when(baggageRepository.findById(nonExistingBaggageId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            baggageService.removeBaggage(nonExistingBaggageId);
        });

        verify(baggageRepository, times(1)).findById(nonExistingBaggageId);
        verify(baggageRepository, never()).deleteById(anyLong());

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Baggage not found with id: " + nonExistingBaggageId, exception.getReason());
    }



    @Test
    void getBaggageByReservation() {
        Long reservationId = 1L;
        List<Baggage> baggageList = new ArrayList<>();

        when(baggageRepository.getBaggageByReservation(any())).thenReturn(baggageList);

        List<Baggage> result = baggageService.getBaggageByReservation(reservationId);

        verify(baggageRepository, times(1)).getBaggageByReservation(any());

        assertNotNull(result);
        assertEquals(baggageList, result);
    }

    @Test
    void getBaggageById() {
        Long baggageId = 1L;

        when(baggageRepository.findById(any())).thenReturn(Optional.of(new Baggage()));

        Baggage result = baggageService.getBaggageById(baggageId);

        verify(baggageRepository, times(1)).findById(any());

        assertNotNull(result);
    }

    @Test
    void getBaggageById_BaggageNotFoundException() {
        Long baggageId = 1L;

        when(baggageRepository.findById(any())).thenReturn(Optional.empty());

        BaggageNotFoundException exception = assertThrows(BaggageNotFoundException.class,
                () -> baggageService.getBaggageById(baggageId));

        verify(baggageRepository, times(1)).findById(any());

        assertNotNull(exception);
        assertTrue(exception.getMessage().contains("Baggage not found with id: 1"));
    }

    @Test
    void getBaggageByQuery() {
        CreateBaggageRequest query = new CreateBaggageRequest();
        query.setReservationId(1L);

        List<Baggage> expectedBaggages = Collections.singletonList(new Baggage());

        when(baggageRepository.getBaggageByReservation(anyLong())).thenReturn(expectedBaggages);

        List<Baggage> result = baggageService.getBaggageByQuery(query);

        assertEquals(expectedBaggages, result);
    }
}