package com.awesomeorg.airlineservice.integration_tests;

import com.awesomeorg.airlineservice.AbstractIntegrationTest;
import com.awesomeorg.airlineservice.entity.Baggage;
import com.awesomeorg.airlineservice.protocol.CreateBaggageRequest;
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


@AutoConfigureMockMvc
public class BaggageControllerIntegrationTest extends AbstractIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    @Transactional
    public void testCreateBaggage() {
        CreateBaggageRequest request = new CreateBaggageRequest();
        request.setWeight(14);
        request.setSize(1);
        request.setTypeOfBaggage(Baggage.BaggageType.CHECKED);
        request.setReservationId(3L);

        mockMvc.perform(MockMvcRequestBuilders.post("/baggages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(result -> {
                    if (result.getResponse().getContentAsString().trim().length() > 0) {

                        MockMvcResultMatchers.jsonPath("$.message")
                                .value("Baggage already exists for the given reservation")
                                .match(result);
                    }
                });
    }


    @Test
    @SneakyThrows
    @Transactional
    public void testRemoveBaggage() {
        mockMvc.perform(MockMvcRequestBuilders.delete("/baggages/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @SneakyThrows
    @Transactional
    public void testListByReservation() {
        mockMvc.perform(MockMvcRequestBuilders.get("/baggages")
                        .param("reservationId", "3"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].weight").value(14))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].size").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].typeOfBaggage").value("CHECKED"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].reservationId").value(3));
    }


    @Test
    @SneakyThrows
    @Transactional
    public void testGetById() {
        mockMvc.perform(MockMvcRequestBuilders.get("/baggages/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.weight").value(14))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.typeOfBaggage").value("CHECKED"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.reservationId").value(3));
    }
}