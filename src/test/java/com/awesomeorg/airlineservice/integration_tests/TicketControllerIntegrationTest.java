package com.awesomeorg.airlineservice.integration_tests;

import com.awesomeorg.airlineservice.AbstractIntegrationTest;
import com.awesomeorg.airlineservice.entity.Ticket;
import com.awesomeorg.airlineservice.protocol.TicketQuery;
import com.awesomeorg.airlineservice.protocol.UpdateTicketRequest;
import com.awesomeorg.airlineservice.repository.TicketRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static junit.framework.TestCase.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class TicketControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TicketRepository ticketRepository;

    @Test
    @SneakyThrows
    public void testFindFreeTicket() {
        Ticket ticket = new Ticket();
        ticket.setDateOfFlight(LocalDate.now());
        ticket.setDateOfPurchase(LocalDate.now());
        ticket.setDateOfReturn(LocalDate.now());
        ticket.setPriceOfTicket((int) 1450.50);
        ticket.setSeat(2);

        ticketRepository.save(ticket);

        Page<Ticket> result = ticketRepository.findFreeTicket(LocalDate.now(), PageRequest.of(0, 10));


        Ticket expectedTicket = new Ticket();
        expectedTicket.setId(result.getContent().get(0).getId());
        expectedTicket.setDateOfPurchase(LocalDate.now());
        expectedTicket.setDateOfFlight(LocalDate.now());
        expectedTicket.setDateOfReturn(LocalDate.now());
        expectedTicket.setSeat(5);
        expectedTicket.setPriceOfTicket((int) 1500);

        assertEquals(2, result.getTotalElements());
        assertEquals(expectedTicket, result.getContent().get(0));
    }

    @Test
    @SneakyThrows
    public void testCreateTicket() {
        TicketQuery ticketQuery = new TicketQuery();
        ticketQuery.setDateOfFlight(LocalDate.now());
        ticketQuery.setDateOfPurchase(LocalDate.now());
        ticketQuery.setDateOfReturn(LocalDate.now());
        ticketQuery.setPriceOfTicket((int) 1500.60);
        ticketQuery.setSeat(5);

        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        String jsonBody = objectMapper.writeValueAsString(ticketQuery);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isCreated())
                .andReturn();

        Ticket createdTicket = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Ticket.class);
        assertNotNull(createdTicket.getId());
    }

    @Test
    @SneakyThrows
    public void testUpdateTicket() {
        Ticket existingTicket = ticketRepository.findAll().get(0);
        UpdateTicketRequest updateTicketRequest = new UpdateTicketRequest();
        updateTicketRequest.setDateOfFlight(LocalDate.now().plusDays(1));
        updateTicketRequest.setDateOfPurchase(LocalDate.now().plusDays(1));
        updateTicketRequest.setDateOfReturn(LocalDate.now().plusDays(1));
        updateTicketRequest.setPriceOfTicket(1500);
        updateTicketRequest.setSeat(10);

        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        String jsonBody = objectMapper.writeValueAsString(updateTicketRequest);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .put("/tickets/{ticketId}", existingTicket.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isOk())
                .andReturn();

        Ticket updatedTicket = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Ticket.class);
        assertEquals(updateTicketRequest.getDateOfFlight(), updatedTicket.getDateOfFlight());
        assertEquals(updateTicketRequest.getDateOfPurchase(), updatedTicket.getDateOfPurchase());
        assertEquals(updateTicketRequest.getDateOfReturn(), updatedTicket.getDateOfReturn());
        assertEquals(updateTicketRequest.getPriceOfTicket(), updatedTicket.getPriceOfTicket());
        assertEquals(updateTicketRequest.getSeat(), updatedTicket.getSeat());
    }



    @Test
    @SneakyThrows
    public void testDeleteTicket() {
        Ticket existingTicket = ticketRepository.findAll().get(0);

        mockMvc.perform(delete("/tickets/{ticketId}", existingTicket.getId()))
                .andExpect(status().isNoContent());

        assertFalse(ticketRepository.existsById(existingTicket.getId()));
    }
}