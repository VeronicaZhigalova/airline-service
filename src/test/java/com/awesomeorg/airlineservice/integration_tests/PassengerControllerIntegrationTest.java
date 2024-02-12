package com.awesomeorg.airlineservice.integration_tests;

import com.awesomeorg.airlineservice.protocol.CreatePassengerRequest;
import com.awesomeorg.airlineservice.protocol.UpdatePassengerRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;



@SpringBootTest
@AutoConfigureMockMvc
public class PassengerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    @Transactional
    public void testRegisterPassenger() {
        CreatePassengerRequest request = new CreatePassengerRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setPhoneNumber("555-123-4567");
        request.setEmailAddress("john.doe@example.com");

        mockMvc.perform(MockMvcRequestBuilders.post("/passengers/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value("555-123-4567"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.emailAddress").value("john.doe@example.com"));
    }

    @Test
    @SneakyThrows
    @Transactional
    public void testUpdatePassenger() {
        Long passengerId = 1L;

        UpdatePassengerRequest request = new UpdatePassengerRequest();
        request.setFirstName("Julia");
        request.setLastName("My");
        request.setPhoneNumber("555-987-6543");
        request.setEmailAddress("julia.my@example.com");

        mockMvc.perform(MockMvcRequestBuilders.put("/passengers/{passengerId}", passengerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(passengerId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Julia"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("My"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value("555-987-6543"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.emailAddress").value("julia.my@example.com"));
    }

    @Test
    @SneakyThrows
    @Transactional
    public void testDeletePassenger() {
        Long passengerId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/passengers/{passengerId}", passengerId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}