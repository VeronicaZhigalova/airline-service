package com.awesomeorg.airlineservice.repository.specification;

import com.awesomeorg.airlineservice.entity.Reservation;
import com.awesomeorg.airlineservice.protocol.ReservationQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ReservationSpecification {

    public static Specification<Reservation> createSpecification(final ReservationQuery query) {
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


    private static List<Specification<Reservation>> getSpecificationList(final ReservationQuery query) {
        final List<Specification<Reservation>> specifications = new ArrayList<>();

        if (query.getFlightNumber() != null) {
            specifications.add(withEqualsField("flightNumber", query.getFlightNumber()));
        }
        if (query.getDepartureAirport() != null) {
            specifications.add(withEqualsField("departureAirport", query.getDepartureAirport()));
        }
        if (query.getArrivalAirport() != null) {
            specifications.add(withEqualsField("arrivalAirport", query.getArrivalAirport()));
        }
        if (query.getDepartureTime() != null) {
            specifications.add(withEqualsField("departureTime", query.getDepartureTime()));
        }
        if (query.getArrivalTime() != null) {
            specifications.add(withEqualsField("arrivalTime", query.getArrivalTime()));
        }
        if (query.getTripType() != null) {
            specifications.add(withEqualsField("tripType", query.getTripType()));
        }
        if (query.getDeparture() != null) {
            specifications.add(withEqualsField("departure", query.getDeparture()));
        }
        if (query.getDestination() != null) {
            specifications.add(withEqualsField("destination", query.getDestination()));
        }
        if (query.getNumberOfCustomerSeats() != null) {
            specifications.add(withEqualsField("numberOfCustomerSeats", query.getNumberOfCustomerSeats()));
        }
        if (query.getClassOfFlight() != null) {
            specifications.add(withEqualsField("classOfFlight", query.getClassOfFlight()));
        }
        if (query.getDepartureDate() != null) {
            specifications.add(withEqualsField("departureDate", query.getDepartureDate()));
        }
        if (query.getReturnDate() != null) {
            specifications.add(withEqualsField("returnDate", query.getReturnDate()));
        }
        if (query.getTicketId() != null) {
            specifications.add(withEqualsField("ticketId", query.getTicketId()));
        }
        if (query.getReservationStatus() != null) {
            specifications.add(withEqualsField("reservationStatus", query.getReservationStatus()));
        }
        if (query.getPassengerId() != null) {
            specifications.add(withEqualsField("passengerId", query.getPassengerId()));
        }

        return specifications;
    }

    private static <T> Specification<T> withEqualsField(String fieldName, Object value) {
        return (root, query, builder) -> builder.equal(root.get(fieldName), value);
    }
}