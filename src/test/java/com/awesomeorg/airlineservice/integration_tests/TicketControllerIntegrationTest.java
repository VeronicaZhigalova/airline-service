package com.awesomeorg.airlineservice.integration_tests;


import com.awesomeorg.airlineservice.AbstractIntegrationTest;
import com.awesomeorg.airlineservice.entity.Ticket;
import com.awesomeorg.airlineservice.protocol.UpdateTicketRequest;
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


@AutoConfigureMockMvc
public class TicketControllerIntegrationTest extends AbstractIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    @Transactional
    public void testCreateTicket() {
        Ticket ticketToCreate = new Ticket();
        ticketToCreate.setDateOfFlight(LocalDate.now().plusDays(5));
        ticketToCreate.setDateOfPurchase(LocalDate.now());
        ticketToCreate.setDateOfReturn(LocalDate.now().plusDays(10));
        ticketToCreate.setPriceOfTicket((int) 1500.60);
        ticketToCreate.setSeat(5);

        mockMvc.perform(MockMvcRequestBuilders.post("/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ticketToCreate)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateOfPurchase").value(LocalDate.now().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateOfFlight").value(ticketToCreate.getDateOfFlight().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateOfReturn").value(ticketToCreate.getDateOfReturn().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.seat").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.priceOfTicket").value(1500.0));

    }

    @Test
    @SneakyThrows
    @Transactional
    public void testUpdateTicket() {
        Long existingTicketId = 1L;
        UpdateTicketRequest updateRequest = new UpdateTicketRequest();

        mockMvc.perform(MockMvcRequestBuilders.put("/tickets/{ticketId}", existingTicketId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(existingTicketId));

    }

    @Test
    @SneakyThrows
    @Transactional
    public void testDeleteTicket() {
        Long existingTicketId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/tickets/{ticketId}", existingTicketId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
