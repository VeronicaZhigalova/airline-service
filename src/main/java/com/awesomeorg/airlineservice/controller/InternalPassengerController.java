package com.awesomeorg.airlineservice.controller;

import com.awesomeorg.airlineservice.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/passengers")
@RequiredArgsConstructor
public class InternalPassengerController {

    private final PassengerService passengerService;

}
