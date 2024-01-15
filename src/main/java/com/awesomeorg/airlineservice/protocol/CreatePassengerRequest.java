package com.awesomeorg.airlineservice.protocol;

import lombok.Data;

@Data
public class CreatePassengerRequest {

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String emailAddress;
}
