package com.awesomeorg.airlineservice.service;

import com.awesomeorg.airlineservice.repository.BlocklistRepository;
import com.awesomeorg.airlineservice.repository.PassengerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PassengerService {

    private final PassengerRepository passengerRepository;
    private final BlocklistRepository blocklistRepository;
}
