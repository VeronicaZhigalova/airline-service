package com.awesomeorg.airlineservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "baggages")
public class Baggage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer weight;

    private String size;

    private BaggageType typeOfBaggage;

    private Long reservationId;

    public enum BaggageType {
        CHECKED,
        CARRY_ON,
        EXTRA_BAGGAGE ;
    }
}
