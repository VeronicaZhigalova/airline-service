package com.awesomeorg.airlineservice.integration_tests;

import com.awesomeorg.airlineservice.AbstractIntegrationTest;
import com.awesomeorg.airlineservice.entity.Baggage;
import com.awesomeorg.airlineservice.protocol.CreateBaggageRequest;
import com.awesomeorg.airlineservice.service.BaggageService;
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

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@AutoConfigureMockMvc
public class BaggageControllerIntegrationTest extends AbstractIntegrationTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BaggageService baggageService;

    @Test
    @SneakyThrows
    @DirtiesContext
    public void testCreateBaggage() {

        CreateBaggageRequest request = new CreateBaggageRequest();
        request.setWeight(14);
        request.setSize(1);
        request.setTypeOfBaggage(Baggage.BaggageType.CHECKED);
        request.setReservationId(3L);


        given(baggageService.createBaggage(any(CreateBaggageRequest.class)))
                .willReturn(new Baggage(request));


        mockMvc.perform(MockMvcRequestBuilders.post("/baggages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"weight\":14,\"size\":1,\"typeOfBaggage\":\"CHECKED\",\"reservationId\":3}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.weight").value(14))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.typeOfBaggage").value("CHECKED"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.reservationId").value(3));


        verify(baggageService, times(1)).createBaggage(any(CreateBaggageRequest.class));
    }


    @Test
    @SneakyThrows
    @DirtiesContext
    public void testRemoveBaggage() {
        mockMvc.perform(MockMvcRequestBuilders.delete("/baggages/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(baggageService, times(1)).removeBaggage(1L);
    }

    @Test
    @SneakyThrows
    @DirtiesContext
    public void testListByReservation() {
        given(baggageService.getBaggageByReservation(3L))
                .willReturn(Arrays.asList(
                        new Baggage(14, 1, Baggage.BaggageType.CHECKED, 3L),
                        new Baggage(6, 2, Baggage.BaggageType.CHECKED, 5L)
                ));

        mockMvc.perform(MockMvcRequestBuilders.get("/baggages")
                        .param("reservationId", "3"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].weight").value(14))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].size").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].typeOfBaggage").value("CHECKED"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].reservationId").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].weight").value(6))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].size").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].typeOfBaggage").value("CHECKED"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].reservationId").value(5));

        verify(baggageService, times(1)).getBaggageByReservation(3L);
    }

    @Test
    @SneakyThrows
    @DirtiesContext
    public void testGetById() {
        given(baggageService.getBaggageById(1L))
                .willReturn(new Baggage(14, 1, Baggage.BaggageType.CHECKED, 3L));

        mockMvc.perform(MockMvcRequestBuilders.get("/baggages/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.weight").value(14))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.typeOfBaggage").value("CHECKED"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.reservationId").value(3));

        verify(baggageService, times(1)).getBaggageById(1L);
    }
}