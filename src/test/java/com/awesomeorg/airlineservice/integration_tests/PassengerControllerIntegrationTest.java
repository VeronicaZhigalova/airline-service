package com.awesomeorg.airlineservice.integration_tests;

import com.awesomeorg.airlineservice.AbstractIntegrationTest;
import com.awesomeorg.airlineservice.entity.Passenger;
import com.awesomeorg.airlineservice.protocol.CreatePassengerRequest;
import com.awesomeorg.airlineservice.protocol.UpdatePassengerRequest;
import com.awesomeorg.airlineservice.repository.PassengerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PassengerControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PassengerRepository passengerRepository;

    @Test
    @SneakyThrows
    public void testRegisterPassenger() {
        CreatePassengerRequest passengerRequest = new CreatePassengerRequest();
        passengerRequest.setFirstName("John");
        passengerRequest.setLastName("Doe");
        passengerRequest.setPhoneNumber("555-123-4567");
        passengerRequest.setEmailAddress("john.doe@example.com");


        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(passengerRequest);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/passengers/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isAccepted())
                .andReturn();

        Passenger registeredPassenger = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), Passenger.class);

        assertNotNull(registeredPassenger.getId());
        assertEquals(passengerRequest.getFirstName(), registeredPassenger.getFirstName());
        assertEquals(passengerRequest.getLastName(), registeredPassenger.getLastName());
        assertEquals(passengerRequest.getPhoneNumber(), registeredPassenger.getPhoneNumber());
        assertEquals(passengerRequest.getEmailAddress(), registeredPassenger.getEmailAddress());

    }

    @Test
    @SneakyThrows
    public void testUpdatePassenger() {
        Passenger existingPassenger = passengerRepository.findAll().get(0);
        UpdatePassengerRequest updatePassengerRequest = new UpdatePassengerRequest();
        updatePassengerRequest.setFirstName("Alice");
        updatePassengerRequest.setLastName("Miracle");
        updatePassengerRequest.setPhoneNumber("549-923-8877");
        updatePassengerRequest.setEmailAddress("alice.miracle@gmail.com");


        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(updatePassengerRequest);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .put("/passengers/{passengerId}", existingPassenger.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isOk())
                .andReturn();

        Passenger updatedPassenger = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), Passenger.class);

        assertEquals(updatePassengerRequest.getFirstName(), updatedPassenger.getFirstName());
        assertEquals(updatePassengerRequest.getLastName(), updatedPassenger.getLastName());
        assertEquals(updatePassengerRequest.getPhoneNumber(), updatedPassenger.getPhoneNumber());
        assertEquals(updatePassengerRequest.getEmailAddress(), updatedPassenger.getEmailAddress());

    }

    @Test
    @SneakyThrows
    public void testDeletePassenger() {
        Passenger existingPassenger = passengerRepository.findAll().get(0);

        mockMvc.perform(delete("/passengers/{passengerId}", existingPassenger.getId()))
                .andExpect(status().isNoContent());

        assertFalse(passengerRepository.existsById(existingPassenger.getId()));
    }
}