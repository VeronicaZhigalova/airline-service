package com.awesomeorg.airlineservice.service;

import com.awesomeorg.airlineservice.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
}
