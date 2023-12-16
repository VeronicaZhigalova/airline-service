package com.awesomeorg.airlineservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer date_of_purchase;
    private Integer date_of_flight;
    private Integer date_of_return;
    private Integer seat;
    private Integer price_of_tickets;
}
