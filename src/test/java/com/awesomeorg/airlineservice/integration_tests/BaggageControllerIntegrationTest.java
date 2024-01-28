package com.awesomeorg.airlineservice.integration_tests;

import com.awesomeorg.airlineservice.AbstractIntegrationTest;
import com.awesomeorg.airlineservice.entity.Baggage;
import com.awesomeorg.airlineservice.protocol.CreateBaggageRequest;
import com.awesomeorg.airlineservice.repository.BaggageRepository;
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


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BaggageControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BaggageRepository baggageRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    public void testRegisterBaggage() {

        CreateBaggageRequest baggageRequest = new CreateBaggageRequest();
        baggageRequest.setWeight(14);
        baggageRequest.setSize(1);
        baggageRequest.setTypeOfBaggage(Baggage.BaggageType.CHECKED);
        baggageRequest.setReservationId(Long.valueOf(3));


        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/baggages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(baggageRequest)))
                .andExpect(status().isCreated())
                .andReturn();


        Baggage registeredBaggage = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), Baggage.class);

        assertNotNull(registeredBaggage.getId());
        assertEquals(baggageRequest.getWeight(), registeredBaggage.getWeight());
        assertEquals(baggageRequest.getSize(), registeredBaggage.getSize());
        assertEquals(baggageRequest.getTypeOfBaggage(), registeredBaggage.getTypeOfBaggage());
        assertEquals(baggageRequest.getReservationId(), registeredBaggage.getReservationId());


        baggageRepository.deleteById(registeredBaggage.getId());
    }



    @Test
    @SneakyThrows
    public void testListBaggageByReservation() {
        Long reservationId = Long.valueOf(5);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/baggages/list")
                        .param("reservationId", reservationId.toString()))
                .andExpect(status().isOk())
                .andReturn();

        List<Baggage> baggageList = Arrays.asList(new ObjectMapper().readValue(
                mvcResult.getResponse().getContentAsString(), Baggage[].class));

        assertEquals(1, baggageList.size());
        assertEquals(reservationId, baggageList.get(0).getReservationId());
    }

    @Test
    @SneakyThrows
    public void testGetBaggageById() {
        Long baggageId = baggageRepository.findAll().get(0).getId();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/baggages/{baggageId}", baggageId))
                .andExpect(status().isOk())
                .andReturn();

        Baggage retrievedBaggage = new ObjectMapper().readValue(
                mvcResult.getResponse().getContentAsString(), Baggage.class);

        assertEquals(baggageId, retrievedBaggage.getId());
    }

    @Test
    @SneakyThrows
    public void testRemoveBaggage() {
        Long baggageId = baggageRepository.findAll().get(0).getId();

        mockMvc.perform(delete("/baggages/{baggageId}", baggageId))
                .andExpect(status().isNoContent());

        assertFalse(baggageRepository.existsById(baggageId));
    }

}
