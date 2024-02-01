package com.awesomeorg.airlineservice.service;

import com.awesomeorg.airlineservice.entity.Baggage;
import com.awesomeorg.airlineservice.exceptions.BaggageAlreadyExistsException;
import com.awesomeorg.airlineservice.exceptions.BaggageNotFoundException;
import com.awesomeorg.airlineservice.protocol.CreateBaggageRequest;
import com.awesomeorg.airlineservice.repository.BaggageRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BaggageService {

    private final BaggageRepository baggageRepository;
    private static final Logger log = LoggerFactory.getLogger(BaggageService.class);

    public Baggage createBaggage(CreateBaggageRequest request) {
                // Check if baggage already exists for the given reservation
                Long reservationId = request.getReservationId();
                if (reservationId == null) {
                    throw new IllegalArgumentException("Reservation ID cannot be null");
                }
                final Optional<Baggage> optionalBaggage = baggageRepository.findByReservationId(reservationId);
                if (optionalBaggage.isPresent()) {
                    throw new BaggageAlreadyExistsException("Baggage already exists for the given reservation");
                }

                // If not exists, create and save the new baggage
                final Baggage baggage = new Baggage(request);
                return baggageRepository.save(baggage);
        }

    public void removeBaggage(Long baggageId) {
        // Check if baggage with the given baggageId exists
        Baggage baggage = getBaggageById(baggageId);

        // If exists, remove the baggage
        baggageRepository.deleteById(baggageId);
    }

    public List<Baggage> getBaggageByReservation(Long reservationId) {
        // Retrieve a list of baggage by the given reservationId
        return baggageRepository.getBaggageByReservation(reservationId);
    }


    public Baggage getBaggageById(Long baggageId) {
        // Retrieve a baggage by the given baggageId
        return baggageRepository.findById(baggageId)
                .orElseThrow(() -> new BaggageNotFoundException("Baggage not found with id: " + baggageId));
    }
}
