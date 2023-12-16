package com.awesomeorg.airlineservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Entity(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String flight_number;

    private String departure_airport;

    private String arrival_airport;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate departure_time;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate arrival_time;

    private String trip_type;

    private String from_where;

    private String to_where;

    private Integer number_of_customer_seats;

    private String class_of_flight;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate depart_date;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate return_date;

    @Enumerated(EnumType.STRING)
    private Status reservation_status;

    private Long seat_id;

    private Long passenger_id;

    public enum Status {
        PENDING,
        CONFIRMED,
        CANCELLED,
        NOT_ARRIVED,
        FINISHED
    }
}
