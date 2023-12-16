package com.awesomeorg.airlineservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "passenger")
@NoArgsConstructor
public class Passengers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String first_name;

    private String last_name;

    private String phone_number;

    private String email_address;
}
