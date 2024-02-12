package com.awesomeorg.airlineservice.entity;

import com.awesomeorg.airlineservice.protocol.CreateBaggageRequest;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "baggages")
@NoArgsConstructor
public class Baggage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer weight;

    private Integer size;

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
