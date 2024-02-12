package com.awesomeorg.airlineservice.entity;

import com.awesomeorg.airlineservice.protocol.ReservationQuery;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity(name = "reservations")
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String flightNumber;

    private String departureAirport;

    private String arrivalAirport;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime departureTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime arrivalTime;

    private String tripType;

    private String departure;

    private String destination;

    private Integer numberOfCustomerSeats;

    @Enumerated(EnumType.STRING)
    private FlightClass classOfFlight;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate departureDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate returnDate;

    @Enumerated(EnumType.STRING)
    private Status reservationStatus;

    @Column(name="ticket_id")
    private Long ticketId;

    @Column(name = "passenger_id")
    private Long passengerId;

    public Reservation(final ReservationQuery request,
                       final Long passengerId) {
        this.flightNumber = request.getFlightNumber();
        this.departureAirport = request.getDepartureAirport();
        this.arrivalAirport = request.getArrivalAirport();
        this.departureTime = request.getDepartureTime();
        this.arrivalTime = request.getArrivalTime();
        this.tripType = request.getTripType();
        this.departure = request.getDeparture();
        this.destination = request.getDestination();
        this.numberOfCustomerSeats = request.getNumberOfCustomerSeats();
        this.classOfFlight = request.getClassOfFlight();
        this.departureDate = request.getDepartureDate();
        this.returnDate = request.getReturnDate();
        this.reservationStatus = Status.PENDING;
        this.ticketId = request.getTicketId();
        this.passengerId = passengerId;

    }


    public enum Status {
        PENDING,
        CONFIRMED,
        CANCELLED,
        NOT_ARRIVED,
        FINISHED
    }

    public enum FlightClass {
        ECONOMY,
        BUSINESS,
        FIRST;
    }
}
