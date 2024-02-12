package com.awesomeorg.airlineservice.protocol;

import lombok.Data;

@Data
public class UpdatePassengerRequest {

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String emailAddress;
}
