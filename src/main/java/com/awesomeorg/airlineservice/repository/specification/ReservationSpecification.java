package com.awesomeorg.airlineservice.repository.specification;

import com.awesomeorg.airlineservice.entity.Reservation;
import com.awesomeorg.airlineservice.repository.InternalReservationQuery;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ReservationSpecification {

    public static Specification<Reservation> createSpecification(final InternalReservationQuery query) {
        Specification<Reservation> specification = null;
        final List<Specification<Reservation>> specifications = getSpecificationList(query);
        for (final Specification<Reservation> s : specifications) {
            if (specification == null) {
                specification = Specification.where(s);
            } else {
                specification = specification.and(s);
            }
        }
        return specification;
    }

    private static List<Specification<Reservation>> getSpecificationList(final InternalReservationQuery query) {
        final List<Specification<Reservation>> specifications = new ArrayList<>();

        if (query.getReservationStatus() != null) {
            Specification<Reservation> specification = Specification
                    .where(withEqualsField(new ReservationSearchCriteria("reservationStatus", query.getReservationStatus())));
            specifications.add(specification);
        }
        if (query.getReservationDate() != null) {
            Specification<Reservation> specification = Specification
                    .where(withEqualsField(new ReservationSearchCriteria("reservationDate", query.getReservationDate())));
            specifications.add(specification);
        }

        if (query.getFlightNumber() != null) {
            Specification<Reservation> specification = Specification
                    .where(withEqualsField(new ReservationSearchCriteria("flightNumber", query.getFlightNumber())));
            specifications.add(specification);
        }

        if (query.getDepartureAirport() != null) {
            Specification<Reservation> specification = Specification
                    .where(withEqualsField(new ReservationSearchCriteria("departureAirport", query.getDepartureAirport())));
            specifications.add(specification);
        }
        if (query.getArrivalAirport() != null) {
            Specification<Reservation> specification = Specification
                    .where(withEqualsField(new ReservationSearchCriteria("arrivalAirport", query.getArrivalAirport())));
            specifications.add(specification);
        }
        if (query.getDepartureTime() != null) {
            Specification<Reservation> specification = Specification
                    .where(withEqualsField(new ReservationSearchCriteria("departureTime", query.getDepartureTime())));
            specifications.add(specification);
        }
        if (query.getArrivalTime() != null) {
            Specification<Reservation> specification = Specification
                    .where(withEqualsField(new ReservationSearchCriteria("arrivalTime", query.getArrivalTime())));
            specifications.add(specification);
        }
        if (query.getTripType() != null) {
            Specification<Reservation> specification = Specification
                    .where(withEqualsField(new ReservationSearchCriteria("tripType", query.getTripType())));
            specifications.add(specification);
        }
        if (query.getDeparture() != null) {
            Specification<Reservation> specification = Specification
                    .where(withEqualsField(new ReservationSearchCriteria("departure", query.getDeparture())));
            specifications.add(specification);
        }
        if (query.getDestination() != null) {
            Specification<Reservation> specification = Specification
                    .where(withEqualsField(new ReservationSearchCriteria("destination", query.getDestination())));
            specifications.add(specification);
        }
        if (query.getNumberOfCustomerSeats() != null && query.getNumberOfCustomerSeats() != 0) {
            Specification<Reservation> specification = Specification
                    .where(withEqualsField(new ReservationSearchCriteria("numberOfCustomerSeats", query.getNumberOfCustomerSeats())));
            specifications.add(specification);
        }
        if (query.getClassOfFlight() != null) {
            Specification<Reservation> specification = Specification
                    .where(withEqualsField(new ReservationSearchCriteria("classOfFlight", query.getClassOfFlight())));
            specifications.add(specification);
        }
        if (query.getDepartureDate() != null) {
            Specification<Reservation> specification = Specification
                    .where(withEqualsField(new ReservationSearchCriteria("departureDate", query.getDepartureDate())));
            specifications.add(specification);
        }
        if (query.getReturnDate() != null) {
            Specification<Reservation> specification = Specification
                    .where(withEqualsField(new ReservationSearchCriteria("returnDate", query.getReturnDate())));
            specifications.add(specification);
        }
        if (query.getSeatId() != null && query.getSeatId() != 0) {
            Specification<Reservation> specification = Specification
                    .where(withEqualsField(new ReservationSearchCriteria("seatId", query.getSeatId())));
            specifications.add(specification);
        }
        if (query.getPassengerId() != null && query.getPassengerId() != 0) {
            Specification<Reservation> specification = Specification
                    .where(withEqualsField(new ReservationSearchCriteria("passengerId", query.getPassengerId())));
            specifications.add(specification);
        }

        return specifications;
    }

    private static Specification<Reservation> withEqualsField(final ReservationSearchCriteria criteria) {
        return ((root, query, builder) -> builder.equal(root.get(criteria.getKey()), criteria.getValue()));
    }
}