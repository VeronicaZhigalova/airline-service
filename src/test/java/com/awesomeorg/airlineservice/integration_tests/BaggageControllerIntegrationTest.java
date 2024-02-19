package com.awesomeorg.airlineservice.integration_tests;

import com.awesomeorg.airlineservice.AbstractIntegrationTest;
import com.awesomeorg.airlineservice.entity.Baggage;
import com.awesomeorg.airlineservice.protocol.CreateBaggageRequest;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
public class BaggageControllerIntegrationTest extends AbstractIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    @Transactional
    public void testCreateBaggage() {
        CreateBaggageRequest request = new CreateBaggageRequest();
        request.setWeight(14);
        request.setSize(1);
        request.setTypeOfBaggage(Baggage.BaggageType.CHECKED);
        request.setReservationId(3L);

        mockMvc.perform(post("/baggages")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }


    @Test
    @SneakyThrows
    @Transactional
    public void testRemoveBaggage() {
        mockMvc.perform(delete("/baggages/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @SneakyThrows
    @Transactional
    public void testListByReservation() {
        mockMvc.perform(MockMvcRequestBuilders.get("/baggages")
                        .param("reservationId", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].weight").value(14))
                .andExpect(jsonPath("$[0].size").value(1))
                .andExpect(jsonPath("$[0].typeOfBaggage").value("CHECKED"))
                .andExpect(jsonPath("$[0].reservationId").value(3));
    }

    @Test
    @SneakyThrows
    @Transactional
    public void testGetById() {
        mockMvc.perform(MockMvcRequestBuilders.get("/baggages/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.weight").value(14))
                .andExpect(jsonPath("$.size").value(1))
                .andExpect(jsonPath("$.typeOfBaggage").value("CHECKED"))
                .andExpect(jsonPath("$.reservationId").value(3));
    }
}