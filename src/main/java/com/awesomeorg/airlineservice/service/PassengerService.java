package com.awesomeorg.airlineservice.service;

import com.awesomeorg.airlineservice.entity.BlocklistedCustomer;
import com.awesomeorg.airlineservice.entity.Passenger;
import com.awesomeorg.airlineservice.exceptions.CustomerAlreadyExistsException;
import com.awesomeorg.airlineservice.protocol.CreatePassengerRequest;
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
            throw new CustomerAlreadyExistsException("Passenger already exists with this phone number");
        }
        final Passenger passenger = new Passenger(request);
        return passengerRepository.save(passenger);
    }

    public Optional<BlocklistedCustomer> findBlockListedPassenger(final Long passengerId) {
        return blocklistRepository.findBlocklistedCustomerByCustomerId(passengerId);
    }
}