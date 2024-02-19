package com.awesomeorg.airlineservice.service;

import com.awesomeorg.airlineservice.entity.Reservation;
import com.awesomeorg.airlineservice.entity.Ticket;
import com.awesomeorg.airlineservice.exceptions.BadRequestException;
import com.awesomeorg.airlineservice.exceptions.ReservationNotFoundException;
import com.awesomeorg.airlineservice.exceptions.SeatCapacityExceededException;
import com.awesomeorg.airlineservice.exceptions.TicketNotFoundException;
import com.awesomeorg.airlineservice.protocol.InternalReservationQuery;
import com.awesomeorg.airlineservice.protocol.ReservationQuery;
import com.awesomeorg.airlineservice.protocol.UpdateReservationRequest;
import com.awesomeorg.airlineservice.repository.ReservationRepository;
import com.awesomeorg.airlineservice.repository.specification.ReservationSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final PassengerService passengerService;
    private final TicketService ticketService;

        public Reservation createReservation(ReservationQuery query, Long passengerId) {
            validateCreateReservation(query, passengerId);

            Reservation reservation = new Reservation(query, passengerId);
            return reservationRepository.save(reservation);
        }


        private void validateCreateReservation(ReservationQuery request, Long passengerId) {
            validateBlocklistedCustomer(passengerId);
            findAndValidateTicket(request.getTicketId(), request.getNumberOfCustomerSeats());
            checkExistingReservations(passengerId, request);
        }

        private void validateBlocklistedCustomer(Long passengerId) {
            passengerService.findBlockListedPassenger(passengerId)
                    .ifPresent(blocklistedCustomer -> {
                        throw new BadRequestException(String.format("Passenger %d is blocklisted", passengerId));
                    });
        }

        private void findAndValidateTicket(Long ticketId, Integer numberOfCustomerSeats) {
            Ticket ticket = ticketService.findTicketById(ticketId)
                    .orElseThrow(() -> new TicketNotFoundException("Ticket not found with ID: " + ticketId));

            if (ticket.getSeat() > numberOfCustomerSeats) {
                throw new SeatCapacityExceededException(String.format("Ticket %d cannot accommodate %d guests", ticketId, numberOfCustomerSeats));
            }
        }


    private void checkExistingReservations(Long passengerId, ReservationQuery request) {
            InternalReservationQuery query = new InternalReservationQuery();
            query.setPassengerId(passengerId);
            query.setArrivalAirport(request.getArrivalAirport());
            query.setArrivalTime(LocalDate.from(request.getArrivalTime()));
            query.setDeparture(request.getDeparture());
            query.setDestination(request.getDestination());
            query.setClassOfFlight(request.getClassOfFlight());
            query.setDepartureAirport(request.getDepartureAirport());
            query.setFlightNumber(request.getFlightNumber());
            query.setDepartureDate(request.getDepartureDate());

            Page<Reservation> reservationsPage = findReservations(request, Pageable.unpaged());

            if (reservationsPage.getTotalElements() > 0) {
                throw new BadRequestException(String.format("Passenger %d already has a reservation for the %s", passengerId, request.getFlightNumber()));
            }
        }

    public void deleteReservation(Long reservationId) {

        Optional<Reservation> reservation = findReservation(reservationId);


        if (reservation.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found with ID: " + reservationId);
        }

        reservationRepository.deleteById(reservationId);
    }


        public Page<Reservation> findReservations(ReservationQuery query, Pageable pageRequest) {
            Specification<Reservation> specification = ReservationSpecification.createSpecification(query);
            return reservationRepository.findAll(specification, pageRequest);
        }

        public Optional<Reservation> findReservation(Long reservationId) {
            return reservationRepository.findById(reservationId);
        }

        public Reservation updateReservation(Long reservationId, UpdateReservationRequest request, Long passengerId) {
            Optional<Reservation> optionalReservation = findReservation(reservationId);
            if (optionalReservation.isPresent()) {
                Reservation reservation = optionalReservation.get();
                updateReservationFields(reservation, request, passengerId);
                return reservationRepository.save(reservation);
            } else {
                throw new ReservationNotFoundException("Reservation not found with ID: " + reservationId);
            }
        }

        private void updateReservationFields(Reservation reservation, UpdateReservationRequest request, Long passengerId) {
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
        }


        public List<Reservation> findReservation(String departure, String destination, LocalDate departureDate) {
            return reservationRepository.findReservationsByDepartureDestinationAndDate(departure, destination, departureDate);
        }

    public List<Reservation> findReservation(ReservationQuery query) {
        Specification<Reservation> specification = ReservationSpecification.createSpecification(query);
        return reservationRepository.findAll(specification);
    }

    }
