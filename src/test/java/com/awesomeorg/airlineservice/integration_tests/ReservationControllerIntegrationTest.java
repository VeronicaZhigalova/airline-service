package com.awesomeorg.airlineservice.integration_tests;

import com.awesomeorg.airlineservice.AbstractIntegrationTest;
import com.awesomeorg.airlineservice.entity.Passenger;
import com.awesomeorg.airlineservice.entity.Reservation;
import com.awesomeorg.airlineservice.protocol.CreatePassengerRequest;
import com.awesomeorg.airlineservice.protocol.CreateReservationRequest;
import com.awesomeorg.airlineservice.repository.PassengerRepository;
import com.awesomeorg.airlineservice.repository.ReservationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class ReservationControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    public void testCreateReservation() {
        // Register a passenger
        CreatePassengerRequest passengerRequest = new CreatePassengerRequest();
        passengerRequest.setFirstName("John");
        passengerRequest.setLastName("Doe");
        passengerRequest.setPhoneNumber("555-123-4567");
        passengerRequest.setEmailAddress("john.doe@example.com");

        // Perform passenger registration
        MvcResult passengerRegistrationResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/passengers/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passengerRequest)))
                .andExpect(status().isAccepted())
                .andReturn();

        // Extract the registered passenger
        Passenger registeredPassenger = objectMapper.readValue(
                passengerRegistrationResult.getResponse().getContentAsString(), Passenger.class);

        assertNotNull(registeredPassenger.getId());

        // Create a reservation using the registered passenger
        CreateReservationRequest reservationRequest = new CreateReservationRequest();
        reservationRequest.setFlightNumber("ABC123");
        reservationRequest.setDepartureAirport("JFK");
        reservationRequest.setArrivalAirport("LAX");
        reservationRequest.setDepartureTime(LocalDateTime.parse("2023-04-15T08:00:00"));
        reservationRequest.setArrivalTime(LocalDateTime.parse("2023-04-15T12:00:00"));
        reservationRequest.setTripType("One-way");
        reservationRequest.setDeparture("New York");
        reservationRequest.setDestination("Los Angeles");
        reservationRequest.setNumberOfCustomerSeats(2);
        reservationRequest.setClassOfFlight(Reservation.FlightClass.BUSINESS);
        reservationRequest.setDepartureDate(LocalDate.parse("2023-04-15"));
        reservationRequest.setReturnDate(null);
        reservationRequest.setReservationStatus(Reservation.Status.CONFIRMED);
        reservationRequest.setTicketId(1L);
        reservationRequest.setPassengerId(registeredPassenger.getId());

        // Perform reservation creation
        MvcResult reservationCreationResult = this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/reservations")
                        .header("passenger-id", String.valueOf(registeredPassenger.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservationRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        // Extract the created reservation
        String reservationJsonString = reservationCreationResult.getResponse().getContentAsString();
        Reservation createdReservation = objectMapper.readValue(reservationJsonString, Reservation.class);

        // Assertions for the created reservation
        assertEquals(Reservation.Status.CONFIRMED, createdReservation.getReservationStatus());
        assertEquals(registeredPassenger.getId(), createdReservation.getPassengerId());
    }
    @Test
    @SneakyThrows
    public void testCancelReservation() {
        Reservation existingReservation = reservationRepository.findAll().get(0);

        try {
            mockMvc.perform(delete("/reservations/{reservationId}", existingReservation.getId()))
                    .andExpect(status().isNoContent());
        } catch (Exception e) {
            e.printStackTrace();
        }


        Optional<Reservation> canceledReservation = reservationRepository.findById(existingReservation.getId());
        assertTrue(canceledReservation.isPresent(), "Reservation should be present");
        assertEquals("CANCELLED", canceledReservation.get().getReservationStatus(), "Reservation status should be CANCELLED");
    }
}
