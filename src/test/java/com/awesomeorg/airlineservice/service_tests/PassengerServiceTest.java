package com.awesomeorg.airlineservice.service_tests;

import com.awesomeorg.airlineservice.entity.BlocklistedCustomer;
import com.awesomeorg.airlineservice.entity.Passenger;
import com.awesomeorg.airlineservice.exceptions.CustomerAlreadyExistsException;
import com.awesomeorg.airlineservice.exceptions.PassengerNotFoundException;
import com.awesomeorg.airlineservice.protocol.CreatePassengerRequest;
import com.awesomeorg.airlineservice.protocol.UpdatePassengerRequest;
import com.awesomeorg.airlineservice.repository.BlocklistRepository;
import com.awesomeorg.airlineservice.repository.PassengerRepository;
import com.awesomeorg.airlineservice.service.PassengerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class PassengerServiceTest {
    @Mock
    private PassengerRepository passengerRepository;

    @Mock
    private BlocklistRepository blocklistRepository;

    @InjectMocks
    private PassengerService passengerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPassenger() {
        CreatePassengerRequest request = new CreatePassengerRequest();
        request.setEmailAddress("test@example.com");

        when(passengerRepository.findByEmail(request.getEmailAddress())).thenReturn(Optional.empty());
        when(passengerRepository.save(any())).thenReturn(new Passenger(request));

        Passenger createdPassenger = passengerService.createPassenger(request);

        verify(passengerRepository, times(1)).findByEmail(request.getEmailAddress());
        verify(passengerRepository, times(1)).save(any());

        assertNotNull(createdPassenger);
        assertEquals(request.getEmailAddress(), createdPassenger.getEmailAddress());
    }

    @Test
    void createPassenger_CustomerAlreadyExistsException() {
        CreatePassengerRequest request = new CreatePassengerRequest();
        request.setEmailAddress("existing@example.com");

        when(passengerRepository.findByEmail(request.getEmailAddress())).thenReturn(Optional.of(new Passenger()));

        CustomerAlreadyExistsException exception = assertThrows(CustomerAlreadyExistsException.class, () -> {
            passengerService.createPassenger(request);
        });

        assertTrue(exception.getMessage().contains("Passenger already exists with this email"));
    }

    @Test
    void findBlockListedPassenger() {
        Long passengerId = 1L;

        when(blocklistRepository.findBlocklistedCustomerByCustomerId(passengerId)).thenReturn(Optional.empty());

        Optional<BlocklistedCustomer> result = passengerService.findBlockListedPassenger(passengerId);

        verify(blocklistRepository, times(1)).findBlocklistedCustomerByCustomerId(passengerId);

        assertNotNull(result);
        assertFalse(result.isPresent());
    }

    @Test
    void deletePassenger() {
        Long passengerId = 1L;

        when(passengerRepository.findById(any())).thenReturn(Optional.of(new Passenger()));

        assertDoesNotThrow(() -> passengerService.deletePassenger(passengerId));

        verify(passengerRepository, times(1)).findById(any());
        verify(passengerRepository, times(1)).deleteById(any());
    }

    @Test
    void deletePassenger_PassengerNotFoundException() {

        long nonExistingPassengerId = 2L;

        when(passengerRepository.findById(nonExistingPassengerId)).thenReturn(Optional.empty());

        assertThrows(PassengerNotFoundException.class, () -> {
            passengerService.deletePassenger(nonExistingPassengerId);
        });
    }


    @Test
    void updatePassenger() {
        Long passengerId = 1L;
        UpdatePassengerRequest request = new UpdatePassengerRequest();
        request.setFirstName("John");

        Optional<Passenger> optionalPassenger = Optional.of(new Passenger());

        when(passengerRepository.findById(passengerId)).thenReturn(optionalPassenger);
        when(passengerRepository.save(any())).thenAnswer(invocation -> {
            Passenger savedPassenger = invocation.getArgument(0);
            return savedPassenger;
        });


        Passenger result = passengerService.updatePassenger(passengerId, request);

        verify(passengerRepository, times(1)).findById(passengerId);
        verify(passengerRepository, times(1)).save(any());

        assertNotNull(result);
        assertEquals(request.getFirstName(), result.getFirstName());
    }

    @Test
    void updatePassenger_PassengerNotFoundException() {
        Long nonExistingPassengerId = 2L;
        UpdatePassengerRequest request = new UpdatePassengerRequest();

        when(passengerRepository.findById(nonExistingPassengerId)).thenReturn(Optional.empty());

        assertThrows(PassengerNotFoundException.class, () -> {
            passengerService.updatePassenger(nonExistingPassengerId, request);
        });
    }


    @Test
    void findPassengerById() {
        Long passengerId = 1L;

        when(passengerRepository.findById(passengerId)).thenReturn(Optional.of(new Passenger()));

        Optional<Passenger> result = passengerService.findPassengerById(passengerId);

        verify(passengerRepository, times(1)).findById(passengerId);

        assertNotNull(result);
        assertTrue(result.isPresent());
    }

    @Test
    void findPassengerById_NotFound() {
        Long nonExistingPassengerId = 2L;

        when(passengerRepository.findById(nonExistingPassengerId)).thenReturn(Optional.empty());

        Optional<Passenger> result = passengerService.findPassengerById(nonExistingPassengerId);

        verify(passengerRepository, times(1)).findById(nonExistingPassengerId);

        assertNotNull(result);
        assertFalse(result.isPresent());
    }
}