package com.awesomeorg.airlineservice.protocol;

import lombok.Data;

@Data
public class BlockListedCustomerRequest {

    private String reason;

    private Long customerId;
}
