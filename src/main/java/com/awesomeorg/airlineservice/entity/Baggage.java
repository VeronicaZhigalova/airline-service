package com.awesomeorg.airlineservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "baggages")
public class Baggage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer weight;

    private String size;

    @Enumerated(EnumType.STRING)
    private BaggageType typeOfBaggage;

    private Long reservationId;

    public enum BaggageType {
        CHECKED,
        CARRY_ON,
        EXTRA_BAGGAGE ;
    }
}
