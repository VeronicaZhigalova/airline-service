package com.awesomeorg.airlineservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "blocklisted_customers")
public class BlocklistedCustomer{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reason;

    private Long customerId;

}

