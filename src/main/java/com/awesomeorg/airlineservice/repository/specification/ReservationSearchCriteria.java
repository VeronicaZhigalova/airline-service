package com.awesomeorg.airlineservice.repository.specification;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReservationSearchCriteria {

    private String key;
    private Object value;
}
