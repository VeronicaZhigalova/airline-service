package com.awesomeorg.airlineservice.controller;


import com.awesomeorg.airlineservice.service.BaggagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/baggage")
@RequiredArgsConstructor
public class BaggagesController {

    private final BaggagesService baggagesService;
}
