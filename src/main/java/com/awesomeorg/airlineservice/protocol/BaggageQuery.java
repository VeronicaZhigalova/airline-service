package com.awesomeorg.airlineservice.protocol;

import com.awesomeorg.airlineservice.entity.Baggage;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class BaggageQuery {

    private Integer weight;

    private Integer size;

    @Enumerated(EnumType.STRING)
    private Baggage.BaggageType typeOfBaggage;

    private Long reservationId;

}
