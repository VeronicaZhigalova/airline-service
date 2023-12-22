package com.awesomeorg.airlineservice.controller;


import com.awesomeorg.airlineservice.service.BaggageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/baggages")
@RequiredArgsConstructor
public class BaggageController {

    private final BaggageService baggageService;
}
