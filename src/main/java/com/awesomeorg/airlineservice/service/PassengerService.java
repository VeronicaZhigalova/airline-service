package com.awesomeorg.airlineservice.service;

import com.awesomeorg.airlineservice.entity.BlocklistedCustomer;
import com.awesomeorg.airlineservice.entity.Passenger;
import com.awesomeorg.airlineservice.exceptions.CustomerAlreadyExistsException;
import com.awesomeorg.airlineservice.exceptions.PassengerNotFoundException;
import com.awesomeorg.airlineservice.protocol.CreatePassengerRequest;
import com.awesomeorg.airlineservice.protocol.UpdatePassengerRequest;
import com.awesomeorg.airlineservice.repository.BlocklistRepository;
import com.awesomeorg.airlineservice.repository.PassengerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PassengerService {

    private final PassengerRepository passengerRepository;
    private final BlocklistRepository blocklistRepository;

    public Passenger createPassenger(CreatePassengerRequest request) {
        final Optional<Passenger> optionalPassenger = passengerRepository.findByEmail(request.getEmailAddress());

        if (optionalPassenger.isPresent()) {
            throw new CustomerAlreadyExistsException("Passenger already exists with this email");
        }

        final Passenger passenger = new Passenger(request);
        return passengerRepository.save(passenger);
    }


    public Optional<BlocklistedCustomer> findBlockListedPassenger(final Long passengerId) {
        return blocklistRepository.findBlocklistedCustomerByCustomerId(passengerId);
    }

    public void deletePassenger(Long passengerId) {

        Optional<Passenger> passenger = findPassengerById(passengerId);

        if (passenger.isPresent()) {
            passengerRepository.deleteById(passengerId);
        } else {
            throw new PassengerNotFoundException("Passenger not found with ID: " + passengerId);
        }
    }

    public Passenger updatePassenger(Long passengerId, UpdatePassengerRequest request) {
        Optional<Passenger> optionalPassenger = findPassengerById(passengerId);
        if (optionalPassenger.isPresent()) {
            Passenger passenger = optionalPassenger.get();
            passenger.setFirstName(request.getFirstName());
            passenger.setLastName(request.getLastName());
            passenger.setPhoneNumber(request.getPhoneNumber());
            passenger.setEmailAddress(request.getEmailAddress());
            return passengerRepository.save(passenger);
        } else {
            throw new PassengerNotFoundException("Passenger not found with ID: " + passengerId);
        }
    }

    public Optional<Passenger> findPassengerById(final Long passengerId) {
        return passengerRepository.findById(passengerId);
    }
}