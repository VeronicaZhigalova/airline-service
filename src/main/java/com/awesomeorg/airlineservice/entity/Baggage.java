package com.awesomeorg.airlineservice.entity;

import com.awesomeorg.airlineservice.protocol.CreateBaggageRequest;
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

    public Baggage(final CreateBaggageRequest request) {
        this.weight = request.getWeight();
        this.size = request.getSize();
        this.typeOfBaggage = request.getTypeOfBaggage();
        this.reservationId = request.getReservationId();
    }

    public enum BaggageType {
        CHECKED,
        CARRY_ON,
        EXTRA_BAGGAGE ;
    }
}
