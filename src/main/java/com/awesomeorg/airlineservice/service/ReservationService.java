package com.awesomeorg.airlineservice.service;

import com.awesomeorg.airlineservice.entity.BlocklistedCustomer;
import com.awesomeorg.airlineservice.entity.Reservation;
import com.awesomeorg.airlineservice.entity.Ticket;
import com.awesomeorg.airlineservice.exceptions.*;
import com.awesomeorg.airlineservice.protocol.CreateReservationRequest;
import com.awesomeorg.airlineservice.protocol.UpdateReservationRequest;
import com.awesomeorg.airlineservice.protocol.InternalReservationQuery;
import com.awesomeorg.airlineservice.repository.ReservationRepository;
import com.awesomeorg.airlineservice.repository.specification.ReservationSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;
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

        final Page<Reservation> reservations = findReservations(query,Pageable.unpaged());
        if (!reservations.isEmpty()) {
            throw new BadRequestException(String.format("Passenger %d already has a reservation for the %s",
                    passengerId, request.getFlightNumber()));
        }

        final Reservation reservation = new Reservation(request, passengerId);

        return reservationRepository.save(reservation);
    }


    public void cancelReservation(Long reservationId) {
        // Check if reservation with the given reservationId exists
        Optional<Reservation> reservation = findReservation(reservationId);

        // If exists, delete the reservation
        if (reservation.isPresent()) {
            reservationRepository.deleteById(reservationId);
        } else {
            throw new ReservationNotFoundException("Reservation not found with ID: " + reservationId);
        }
    }


    public Page<Reservation> findReservations(final InternalReservationQuery query,
                                              final Pageable pageRequest) {
        final Specification<Reservation> specification = ReservationSpecification.createSpecification(query);
        return reservationRepository.findAll(specification, pageRequest);
    }

    public Optional<Reservation> findReservation(Long reservationId) {
        return reservationRepository.findById(reservationId);
    }

    public Reservation updateReservation(Long reservationId, UpdateReservationRequest request, Long passengerId) {
        Optional<Reservation> optionalReservation = findReservation(reservationId);
        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();
            reservation.setFlightNumber(request.getFlightNumber());
            reservation.setDepartureAirport(request.getDepartureAirport());
            reservation.setArrivalAirport(request.getArrivalAirport());
            reservation.setDepartureTime(request.getDepartureTime());
            reservation.setArrivalTime(request.getArrivalTime());
            reservation.setTripType(request.getTripType());
            reservation.setDeparture(request.getDeparture());
            reservation.setDestination(request.getDestination());
            reservation.setNumberOfCustomerSeats(request.getNumberOfCustomerSeats());
            reservation.setClassOfFlight(request.getClassOfFlight());
            reservation.setDepartureDate(request.getDepartureDate());
            reservation.setReservationStatus(Reservation.Status.PENDING);
            reservation.setPassengerId(passengerId);
            return reservationRepository.save(reservation);


        } else {
            throw new ReservationNotFoundException("Reservation not found with ID: " + reservationId);
        }
    }

    public List<Reservation> findReservation(String departure, String destination, LocalDate departureDate) {
    return reservationRepository.findAllById(departure,destination,departureDate);
    }
}