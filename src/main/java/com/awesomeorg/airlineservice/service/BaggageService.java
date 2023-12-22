package com.awesomeorg.airlineservice.service;

import com.awesomeorg.airlineservice.repository.BaggageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BaggageService {

    private final BaggageRepository baggagesRepository;
}
