package com.awesomeorg.airlineservice.service_tests;

import com.awesomeorg.airlineservice.entity.Ticket;
import com.awesomeorg.airlineservice.exceptions.TicketNotFoundException;
import com.awesomeorg.airlineservice.protocol.CreateTicketRequest;
import com.awesomeorg.airlineservice.protocol.UpdateTicketRequest;
import com.awesomeorg.airlineservice.repository.TicketRepository;
import com.awesomeorg.airlineservice.service.TicketService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    @Test
    void findTicketById() {
        Long ticketId = 1L;
        Ticket expectedTicket = new Ticket();

        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(expectedTicket));

        Optional<Ticket> result = ticketService.findTicketById(ticketId);

        verify(ticketRepository, times(1)).findById(ticketId);

        assertTrue(result.isPresent());
        assertEquals(expectedTicket, result.get());
    }

    @Test
    void findFreeTicket() {
        CreateTicketRequest query = new CreateTicketRequest();
        query.setDateOfFlight(LocalDate.now());
        PageRequest pageRequest = PageRequest.of(0, 10);

        List<Ticket> expectedTickets = new ArrayList<>();

        when(ticketRepository.findFreeTicket(query.getDateOfFlight(), pageRequest)).thenReturn(new PageImpl<>(expectedTickets));

        Page<Ticket> result = ticketService.findFreeTicket(query, pageRequest);

        verify(ticketRepository, times(1)).findFreeTicket(query.getDateOfFlight(), pageRequest);

        assertNotNull(result);
        assertEquals(expectedTickets, result.getContent());
    }

    @Test
    void createTickets() {
        CreateTicketRequest request = new CreateTicketRequest();
        request.setSeat(3);

        LocalDate dateOfPurchase = LocalDate.now();
        Page<Ticket> existingTickets = new PageImpl<>(new ArrayList<>());

        when(ticketRepository.findFreeTicket(dateOfPurchase, PageRequest.of(0, request.getSeat()))).thenReturn(existingTickets);
        when(ticketRepository.saveAll(anyList())).thenReturn(new ArrayList<>());

        List<Ticket> result = ticketService.createTickets(request);

        verify(ticketRepository, times(1)).findFreeTicket(dateOfPurchase, PageRequest.of(0, request.getSeat()));
        verify(ticketRepository, times(1)).saveAll(anyList());

        assertNotNull(result);
    }


    @Test
    void updateTicket() {
        Long ticketId = 1L;
        UpdateTicketRequest request = new UpdateTicketRequest();
        request.setSeat(1);

        Ticket originalTicket = new Ticket();
        originalTicket.setSeat(0);

        Optional<Ticket> optionalTicket = Optional.of(originalTicket);

        when(ticketRepository.findById(ticketId)).thenReturn(optionalTicket);
        when(ticketRepository.save(any())).thenAnswer(invocation -> {
            Ticket updatedTicket = invocation.getArgument(0);
            assertEquals(request.getSeat(), updatedTicket.getSeat());
            return updatedTicket;
        });

        Ticket result = ticketService.updateTicket(ticketId, request);

        verify(ticketRepository, times(1)).findById(ticketId);
        verify(ticketRepository, times(1)).save(any());

        assertNotNull(result);
        assertEquals(request.getSeat(), result.getSeat());
    }



    @Test
    void updateTicket_TicketNotFoundException() {
        Long ticketId = 1L;
        UpdateTicketRequest request = new UpdateTicketRequest();
        request.setSeat(5);

        when(ticketRepository.findById(ticketId)).thenReturn(Optional.empty());

        TicketNotFoundException exception = assertThrows(TicketNotFoundException.class,
                () -> ticketService.updateTicket(ticketId, request));

        verify(ticketRepository, times(1)).findById(ticketId);

        assertNotNull(exception);
        assertTrue(exception.getMessage().contains("Ticket not found with ID: " + ticketId));
    }

    @Test
    void deleteTicket() {
        Long ticketId = 1L;
        Optional<Ticket> optionalTicket = Optional.of(new Ticket());

        when(ticketRepository.findById(ticketId)).thenReturn(optionalTicket);

        assertDoesNotThrow(() -> ticketService.deleteTicket(ticketId));

        verify(ticketRepository, times(1)).findById(ticketId);
        verify(ticketRepository, times(1)).deleteById(ticketId);
    }

    @Test
    void deleteTicket_TicketNotFoundException() {
        Long ticketId = 1L;

        when(ticketRepository.findById(ticketId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> ticketService.deleteTicket(ticketId));

        verify(ticketRepository, times(1)).findById(ticketId);

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Ticket not found with ID: " + ticketId, exception.getReason());
    }
}