package com.awesomeorg.airlineservice.entity;

import com.awesomeorg.airlineservice.protocol.TicketQuery;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Data
@Entity(name = "tickets")
@NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfPurchase;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfFlight;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfReturn;

    private Integer seat;

    private Integer priceOfTicket;



    public Ticket(final TicketQuery request) {
            this.dateOfPurchase = request.getDateOfPurchase();
            this.dateOfFlight = request.getDateOfFlight();
            this.dateOfReturn = request.getDateOfReturn();
            this.seat = request.getSeat();
            this.priceOfTicket = request.getPriceOfTicket();
    }
}
