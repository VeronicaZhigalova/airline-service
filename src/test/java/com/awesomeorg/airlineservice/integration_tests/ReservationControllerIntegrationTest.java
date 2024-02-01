package com.awesomeorg.airlineservice.integration_tests;

import com.awesomeorg.airlineservice.AbstractIntegrationTest;
import com.awesomeorg.airlineservice.entity.Passenger;
import com.awesomeorg.airlineservice.entity.Reservation;
import com.awesomeorg.airlineservice.entity.Ticket;
import com.awesomeorg.airlineservice.protocol.CreatePassengerRequest;
import com.awesomeorg.airlineservice.protocol.CreateReservationRequest;
import com.awesomeorg.airlineservice.protocol.TicketQuery;
import com.awesomeorg.airlineservice.service.PassengerService;
import com.awesomeorg.airlineservice.service.ReservationService;
import com.awesomeorg.airlineservice.service.TicketService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@AutoConfigureMockMvc
public class ReservationControllerIntegrationTest extends AbstractIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private TicketService ticketService;


    @Test
    @SneakyThrows
    @DirtiesContext
    public void createReservationTest() {

        CreatePassengerRequest passengerRequest = new CreatePassengerRequest();
        passengerRequest.setFirstName("Angela");
        passengerRequest.setLastName("Smith");
        passengerRequest.setPhoneNumber("987-654-3210");
        passengerRequest.setEmailAddress("a.smith@example.com");

        Passenger passenger = passengerService.createPassenger(passengerRequest);


        TicketQuery ticketRequest = new TicketQuery();
        ticketRequest.setDateOfPurchase(LocalDate.parse("2023-01-15"));
        ticketRequest.setDateOfFlight(LocalDate.parse("2024-03-16"));
        ticketRequest.setDateOfReturn(LocalDate.parse("2024-05-17"));
        ticketRequest.setSeat(5);
        ticketRequest.setPriceOfTicket((int) 1500.60);

        Ticket ticket = ticketService.createTicket(ticketRequest);


        CreateReservationRequest reservationRequest = new CreateReservationRequest();
        reservationRequest.setFlightNumber("RKH987");
        reservationRequest.setDepartureAirport("KOR");
        reservationRequest.setArrivalAirport("EST");
        reservationRequest.setDepartureTime(LocalDateTime.parse("2025-03-25T08:00:00"));
        reservationRequest.setArrivalTime(LocalDateTime.parse("2025-03-25T12:00:00"));
        reservationRequest.setTripType("Round-trip");
        reservationRequest.setDeparture("Korea");
        reservationRequest.setDestination("Estonia");
        reservationRequest.setNumberOfCustomerSeats(1);
        reservationRequest.setClassOfFlight(Reservation.FlightClass.BUSINESS);
        reservationRequest.setDepartureDate(LocalDate.parse("2025-03-25"));
        reservationRequest.setReturnDate(LocalDate.parse("2025-05-25"));
        reservationRequest.setReservationStatus(Reservation.Status.CONFIRMED);
        reservationRequest.setTicketId(10L);
        reservationRequest.setPassengerId(8L);


        mockMvc.perform(MockMvcRequestBuilders.post("/reservations")
                        .header("passenger-id", passenger.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"flightNumber\":\"RKH987\",\"departureAirport\":\"KOR\",\"arrivalAirport\":\"EST\",\"departureTime\":\"2025-03-25T08:00:00\",\"arrivalTime\":\"2025-03-25T12:00:00\",\"tripType\":\"Round-trip\",\"departure\":\"Korea\",\"destination\":\"Estonia\",\"numberOfCustomerSeats\":1,\"classOfFlight\":\"BUSINESS\",\"departureDate\":\"2025-03-25\",\"returnDate\":\"2025-05-25\",\"reservationStatus\":\"CONFIRMED\",\"ticketId\":" + ticket.getId() + ",\"passengerId\":" + passenger.getId() + "}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.flightNumber").value("RKH987"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.departureAirport").value("KOR"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.arrivalAirport").value("EST"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.departureTime").value("2025-03-25T08:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.arrivalTime").value("2025-03-25T12:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tripType").value("Round-trip"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.departure").value("Korea"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.destination").value("Estonia"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfCustomerSeats").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.classOfFlight").value("BUSINESS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.departureDate").value("2025-03-25"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnDate").value("2025-05-25"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.reservationStatus").value("CONFIRMED"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ticketId").value(ticket.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.passengerId").value(passenger.getId()));
    }

    @Test
    @SneakyThrows
    @DirtiesContext
    public void deleteReservationTest() {

        long existingReservationId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/reservations/{reservationId}", existingReservationId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());


        Optional<Reservation> deletedReservation = reservationService.findReservation(existingReservationId);
        assertTrue(deletedReservation.isEmpty());
    }
}