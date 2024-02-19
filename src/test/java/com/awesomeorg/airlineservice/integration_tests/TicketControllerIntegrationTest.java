package com.awesomeorg.airlineservice.integration_tests;

import com.awesomeorg.airlineservice.AbstractIntegrationTest;
import com.awesomeorg.airlineservice.protocol.CreateTicketRequest;
import com.awesomeorg.airlineservice.protocol.UpdateTicketRequest;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@AutoConfigureMockMvc
public class TicketControllerIntegrationTest extends AbstractIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    @Transactional
    public void testCreateTicket() {
        CreateTicketRequest request = new CreateTicketRequest();
        request.setDateOfFlight(LocalDate.now().plusDays(5));
        request.setDateOfPurchase(LocalDate.now());
        request.setDateOfReturn(LocalDate.now().plusDays(10));
        request.setPriceOfTicket((int) 1500.60);
        request.setSeat(1);

        mockMvc.perform(post("/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect((ResultMatcher) jsonPath("$.id").exists())
                .andExpect((ResultMatcher) jsonPath("$.dateOfPurchase").value(LocalDate.now().toString()))
                .andExpect((ResultMatcher) jsonPath("$.dateOfFlight").value(request.getDateOfFlight().toString()))
                .andExpect((ResultMatcher) jsonPath("$.dateOfReturn").value(request.getDateOfReturn().toString()))
                .andExpect((ResultMatcher) jsonPath("$.seat").value(1))
                .andExpect((ResultMatcher) jsonPath("$.priceOfTicket").value(1500.0));
    }



    @Test
    @SneakyThrows
    @Transactional
    public void testUpdateTicket() {
        Long existingTicketId = 1L;
        UpdateTicketRequest updateRequest = new UpdateTicketRequest();

        mockMvc.perform(put("/tickets/{ticketId}", existingTicketId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.id").value(existingTicketId));

    }

    @Test
    @SneakyThrows
    @Transactional
    public void testDeleteTicket() {
        Long existingTicketId = 1L;

        mockMvc.perform(delete("/tickets/{ticketId}", existingTicketId))
                .andExpect(status().isNoContent());
    }
}
