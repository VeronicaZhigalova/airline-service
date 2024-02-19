package com.awesomeorg.airlineservice.service;

import com.awesomeorg.airlineservice.entity.Baggage;
import com.awesomeorg.airlineservice.exceptions.BaggageAlreadyExistsException;
import com.awesomeorg.airlineservice.exceptions.BaggageNotFoundException;
import com.awesomeorg.airlineservice.protocol.CreateBaggageRequest;
import com.awesomeorg.airlineservice.repository.BaggageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BaggageService {

    private final BaggageRepository baggageRepository;


    public Baggage createBaggage(CreateBaggageRequest request) {

            Long reservationId = request.getReservationId();
            if (reservationId == null) {
                throw new IllegalArgumentException("Reservation ID cannot be null");
            }

            if (baggageRepository.findByReservationId(reservationId).isPresent()) {
                throw new BaggageAlreadyExistsException("Baggage already exists for the given reservation");
            }

            Baggage baggage = new Baggage(request);
            return baggageRepository.save(baggage);
        }

    public void removeBaggage(Long baggageId) {
        Baggage baggage = baggageRepository.findById(baggageId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Baggage not found with id: " + baggageId));
        baggageRepository.deleteById(baggageId);
    }


    public List<Baggage> getBaggageByReservation(Long reservationId) {
        return baggageRepository.getBaggageByReservation(reservationId);
    }


    public Baggage getBaggageById(Long baggageId) {
        return baggageRepository.findById(baggageId)
                .orElseThrow(() -> new BaggageNotFoundException("Baggage not found with id: " + baggageId));
    }

    public List<Baggage> getBaggageByQuery(CreateBaggageRequest query) {
        Long reservationId = query.getReservationId();
        if (reservationId == null) {
            throw new IllegalArgumentException("Reservation ID cannot be null");
        }

        return baggageRepository.getBaggageByReservation(reservationId);
    }
}


