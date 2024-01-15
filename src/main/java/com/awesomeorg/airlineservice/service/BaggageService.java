package com.awesomeorg.airlineservice.service;

import com.awesomeorg.airlineservice.entity.Baggage;
import com.awesomeorg.airlineservice.exceptions.BaggageAlreadyExistsException;
import com.awesomeorg.airlineservice.protocol.CreateBaggageRequest;
import com.awesomeorg.airlineservice.repository.BaggageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class BaggageService {

    private final BaggageRepository baggageRepository;

        public Baggage createBaggage(CreateBaggageRequest request) {
            final Optional<Baggage> optionalBaggage = baggageRepository.findById(request.getReservationId());
            if (optionalBaggage.isPresent()) {
                throw new BaggageAlreadyExistsException("Baggage already exists with this reservationId");
            }
            final Baggage baggage = new Baggage(request);
            return baggageRepository.save(baggage);
        }
    }