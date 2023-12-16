package com.awesomeorg.airlineservice.service;

import com.awesomeorg.airlineservice.repository.BaggagesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BaggagesService {

    private final BaggagesRepository baggagesRepository;
}
