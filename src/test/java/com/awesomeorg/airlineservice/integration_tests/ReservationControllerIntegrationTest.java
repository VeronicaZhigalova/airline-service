package com.awesomeorg.airlineservice.integration_tests;

import com.awesomeorg.airlineservice.AbstractIntegrationTest;
import com.awesomeorg.airlineservice.protocol.ReservationQuery;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.awesomeorg.airlineservice.entity.Reservation.FlightClass.ECONOMY;
import static com.awesomeorg.airlineservice.entity.Reservation.Status.PENDING;

@AutoConfigureMockMvc
public class ReservationControllerIntegrationTest extends AbstractIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    @Transactional
    public void createReservationTest() {
        mockMvc.perform(MockMvcRequestBuilders.post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("passenger-id", 3L)
                        .content(objectMapper.writeValueAsString(
                                new ReservationQuery("XYZ456", "LHR", "CDG",
                                        LocalDateTime.parse("2023-06-20T15:30:00"),
                                        LocalDateTime.parse("2023-06-20T18:30:00"),
                                        "Round-trip", "London", "Paris", 1,
                                        ECONOMY, LocalDate.parse("2023-06-20"),
                                        LocalDate.parse("2023-06-25"), PENDING, 3L, 2L))))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @SneakyThrows
    public void deleteReservationTest() {
        long existingReservationId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/reservations/{reservationId}", existingReservationId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}