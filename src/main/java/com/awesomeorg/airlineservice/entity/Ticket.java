package com.awesomeorg.airlineservice.entity;

import com.awesomeorg.airlineservice.protocol.TicketQuery;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Entity(name = "tickets")
@NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Integer dateOfPurchase;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Integer dateOfFlight;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Integer dateOfReturn;

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
