package com.awesomeorg.airlineservice.integration_tests;


import com.awesomeorg.airlineservice.AbstractIntegrationTest;
import com.awesomeorg.airlineservice.entity.Ticket;
import com.awesomeorg.airlineservice.protocol.TicketQuery;
import com.awesomeorg.airlineservice.protocol.UpdateTicketRequest;
import com.awesomeorg.airlineservice.service.TicketService;
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

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@AutoConfigureMockMvc
public class TicketControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService ticketService;

    private final Ticket existingTicket = new Ticket();

    @Test
    @SneakyThrows
    @DirtiesContext
    public void testCreateTicket() {
        Ticket ticketToCreate = new Ticket();
        ticketToCreate.setId(1L);
        ticketToCreate.setDateOfFlight(LocalDate.now().plusDays(5));
        ticketToCreate.setDateOfPurchase(LocalDate.now());
        ticketToCreate.setDateOfReturn(LocalDate.now().plusDays(10));
        ticketToCreate.setPriceOfTicket((int) 1500.60);
        ticketToCreate.setSeat(5);

        given(ticketService.createTicket(any(TicketQuery.class))).willReturn(ticketToCreate);

        mockMvc.perform(MockMvcRequestBuilders.post("/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"dateOfFlight\":\"" + ticketToCreate.getDateOfFlight() + "\"," +
                                "\"dateOfReturn\":\"" + ticketToCreate.getDateOfReturn() + "\"," +
                                "\"seat\":" + ticketToCreate.getSeat() + "," +
                                "\"priceOfTicket\":" + ticketToCreate.getPriceOfTicket() + "}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateOfPurchase").value(LocalDate.now().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateOfFlight").value(ticketToCreate.getDateOfFlight().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateOfReturn").value(ticketToCreate.getDateOfReturn().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.seat").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.priceOfTicket").value(1500.0));
    }

    @Test
    @SneakyThrows
    @DirtiesContext
    public void testUpdateTicket() {

        Long existingTicketId = 1L;


        given(ticketService.updateTicket(eq(existingTicketId), any(UpdateTicketRequest.class))).willReturn(existingTicket);

        mockMvc.perform(MockMvcRequestBuilders.put("/tickets/{ticketId}", existingTicketId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(existingTicket.getId()));


        verify(ticketService, times(1)).updateTicket(eq(existingTicketId), any(UpdateTicketRequest.class));
    }



    @Test
    @SneakyThrows
    @DirtiesContext
    public void testDeleteTicket() {
        mockMvc.perform(MockMvcRequestBuilders.delete("/tickets/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(ticketService, times(1)).deleteTicket(1L);
    }
}