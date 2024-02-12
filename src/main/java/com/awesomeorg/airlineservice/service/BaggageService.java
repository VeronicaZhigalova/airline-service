package com.awesomeorg.airlineservice.service;

import com.awesomeorg.airlineservice.entity.Baggage;
import com.awesomeorg.airlineservice.exceptions.BaggageAlreadyExistsException;
import com.awesomeorg.airlineservice.exceptions.BaggageNotFoundException;
import com.awesomeorg.airlineservice.protocol.CreateBaggageRequest;
import com.awesomeorg.airlineservice.repository.BaggageRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

        Baggage baggage = getBaggageById(baggageId);

        baggageRepository.deleteById(baggageId);
    }

    public List<Baggage> getBaggageByReservation(Long reservationId) {
        return baggageRepository.getBaggageByReservation(reservationId);
    }


    public Baggage getBaggageById(Long baggageId) {
        return baggageRepository.findById(baggageId)
                .orElseThrow(() -> new BaggageNotFoundException("Baggage not found with id: " + baggageId));
    }

    public List<Baggage> getBaggageByQuery(CreateBaggageRequest query, Pageable pageable) {
        Long reservationId = query.getReservationId();
        if (reservationId == null) {
            throw new IllegalArgumentException("Reservation ID cannot be null");
        }

        return baggageRepository.getBaggageByReservation(reservationId);
    }

    public static Specification<Baggage> createSpecification(CreateBaggageRequest request) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.equal(root.get("reservationId"), request.getReservationId()));

            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

            return null;
        };
    }



    private static void addEqualPredicate(List<jakarta.persistence.criteria.Predicate> predicates, CriteriaBuilder criteriaBuilder, Path<?> path, Object value) {
        if (value != null) {
            predicates.add(criteriaBuilder.equal(path, value));
        }
    }
}


