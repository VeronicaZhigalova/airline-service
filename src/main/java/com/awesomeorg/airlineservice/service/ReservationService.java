package com.awesomeorg.airlineservice.service;

import com.awesomeorg.airlineservice.entity.BlocklistedCustomer;
import com.awesomeorg.airlineservice.entity.Reservation;
import com.awesomeorg.airlineservice.entity.Ticket;
import com.awesomeorg.airlineservice.exceptions.BadRequestException;
import com.awesomeorg.airlineservice.exceptions.NotFoundException;
import com.awesomeorg.airlineservice.protocol.CreateReservationRequest;
import com.awesomeorg.airlineservice.repository.InternalReservationQuery;
import com.awesomeorg.airlineservice.repository.ReservationRepository;
import com.awesomeorg.airlineservice.repository.specification.ReservationSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final PassengerService passengerService;
    private final TicketService ticketService;

    public Reservation createReservation(final CreateReservationRequest request,
                                         final Long passengerId) throws BadRequestException, NotFoundException {
        final Optional<BlocklistedCustomer> optionalBlocklistedCustomer = passengerService.findBlockListedPassenger(passengerId);
        if (optionalBlocklistedCustomer.isPresent()) {
            throw new BadRequestException(String.format("Passenger %d is blocklisted", passengerId));
        }

        final Optional<Ticket> optionalTicket = ticketService.findTicketById(request.getSeatId());
        if (optionalTicket.isEmpty()) {
            throw new NotFoundException(String.format("Ticket %d not found", request.getSeatId()));
        }

        final Ticket ticket = optionalTicket.get();
        if (ticket.getSeat() > request.getNumberOfCustomerSeats()) {
            throw new BadRequestException("Ticket can't fit so many guests");
        }

        final InternalReservationQuery query = new InternalReservationQuery();
        query.setPassengerId(passengerId);
        query.setArrivalAirport(request.getArrivalAirport());
        query.setArrivalTime(request.getArrivalTime());
        query.setDeparture(request.getDeparture());
        query.setDestination(request.getDestination());
        query.setClassOfFlight(request.getClassOfFlight());
        query.setDepartureAirport(request.getDepartureAirport());
        query.setFlightNumber(request.getFlightNumber());
        query.setDepartureDate(request.getDepartureDate());


        final Page<Reservation> reservations = findReservations(query, PageRequest.of(0, 1));
        if (!reservations.isEmpty()) {
            throw new BadRequestException(String.format("Passenger %d already has a reservation for the %s",
                    passengerId, request.getFlightNumber()));
        }

        final Reservation reservation = new Reservation(request, passengerId);

        return reservationRepository.save(reservation);
    }

    public Page<Reservation> findReservations(final InternalReservationQuery query,
                                              final Pageable pageRequest) {
        final Specification<Reservation> specification = ReservationSpecification.createSpecification(query);
        return reservationRepository.findAll(specification, pageRequest);
    }
}