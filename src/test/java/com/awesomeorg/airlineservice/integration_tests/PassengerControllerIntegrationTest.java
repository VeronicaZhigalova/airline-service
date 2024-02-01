package com.awesomeorg.airlineservice.integration_tests;

import com.awesomeorg.airlineservice.AbstractIntegrationTest;
import com.awesomeorg.airlineservice.entity.Passenger;
import com.awesomeorg.airlineservice.protocol.CreatePassengerRequest;
import com.awesomeorg.airlineservice.protocol.UpdatePassengerRequest;
import com.awesomeorg.airlineservice.service.PassengerService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@AutoConfigureMockMvc
public class PassengerControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PassengerService passengerService;

    @Test
    @SneakyThrows
    @DirtiesContext
    public void testRegisterPassenger() {
        CreatePassengerRequest request = new CreatePassengerRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setPhoneNumber("555-123-4567");
        request.setEmailAddress("john.doe@example.com");

        given(passengerService.createPassenger(any(CreatePassengerRequest.class)))
                .willReturn(new Passenger(request));

        mockMvc.perform(MockMvcRequestBuilders.post("/passengers/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"phoneNumber\":\"555-123-4567\",\"emailAddress\":\"john.doe@example.com\"}"))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value("555-123-4567"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.emailAddress").value("john.doe@example.com"));

        verify(passengerService, times(1)).createPassenger(any(CreatePassengerRequest.class));
    }

    @Test
    @SneakyThrows
    @DirtiesContext
    public void testUpdatePassenger() {
        Long passengerId = 1L;
        UpdatePassengerRequest request = new UpdatePassengerRequest();
        request.setFirstName("Julia");
        request.setLastName("My");
        request.setPhoneNumber("555-987-6543");
        request.setEmailAddress("julia.my@example.com");

        given(passengerService.updatePassenger(eq(passengerId), any(UpdatePassengerRequest.class)))
                .willReturn(new Passenger(request));

        mockMvc.perform(MockMvcRequestBuilders.put("/passengers/{passengerId}", passengerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"UpdatedFirstName\",\"lastName\":\"UpdatedLastName\",\"phoneNumber\":\"555-987-6543\",\"emailAddress\":\"updated.email@example.com\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Julia"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("My"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value("555-987-6543"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.emailAddress").value("julia.my@example.com"));

        verify(passengerService, times(1)).updatePassenger(eq(passengerId), any(UpdatePassengerRequest.class));
    }

    @Test
    @SneakyThrows
    @DirtiesContext
    public void testDeletePassenger() {
        mockMvc.perform(MockMvcRequestBuilders.delete("/passengers/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(passengerService, times(1)).deletePassenger(1L);
    }
}