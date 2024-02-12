package com.awesomeorg.airlineservice.protocol;


import com.awesomeorg.airlineservice.entity.Reservation;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ReservationQuery {

    private String flightNumber;

    private String departureAirport;

    private String arrivalAirport;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime departureTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime arrivalTime;

    private String tripType;

    private String departure;

    private String destination;

    private Integer numberOfCustomerSeats;

    @Enumerated(EnumType.STRING)
    private Reservation.FlightClass classOfFlight;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate departureDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate returnDate;

    private Long ticketId;

    @Enumerated(EnumType.STRING)
    private Reservation.Status reservationStatus;


    private Long passengerId;

    public ReservationQuery(String flightNumber, String departureAirport, String arrivalAirport,
                            LocalDateTime departureTime, LocalDateTime arrivalTime, String tripType,
                            String departure, String destination, Integer numberOfCustomerSeats,
                            Reservation.FlightClass classOfFlight, LocalDate departureDate, LocalDate returnDate,
                            Reservation.Status reservationStatus, Long ticketId, Long passengerId) {
        this.flightNumber = flightNumber;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.tripType = tripType;
        this.departure = departure;
        this.destination = destination;
        this.numberOfCustomerSeats = numberOfCustomerSeats;
        this.classOfFlight = classOfFlight;
        this.departureDate = departureDate;
        this.returnDate = returnDate;
        this.reservationStatus = reservationStatus;
        this.ticketId = ticketId;
        this.passengerId = passengerId;
    }
}

