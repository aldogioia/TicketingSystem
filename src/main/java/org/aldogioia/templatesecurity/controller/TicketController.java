package org.aldogioia.templatesecurity.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aldogioia.templatesecurity.data.dto.creates.TicketCreateDto;
import org.aldogioia.templatesecurity.data.dto.responses.TicketDto;
import org.aldogioia.templatesecurity.security.availability.RateLimit;
import org.aldogioia.templatesecurity.service.interfaces.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RateLimit(permitsPerSecond = 10)
@RequestMapping("/api/v1/ticket")
public class TicketController {
    private final TicketService ticketService;

    @GetMapping("/get-all")
    public ResponseEntity<List<TicketDto>> getAllTickets() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ticketService.getAllTickets());
    }

    @PostMapping
    public ResponseEntity<TicketDto> createTicket(@Valid @RequestBody TicketCreateDto ticketCreateDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ticketService.createTicket(ticketCreateDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> invalidateTicket(@PathVariable String id) {
        ticketService.invalidateTicket(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTicket(@PathVariable String id) {
        ticketService.deleteTicket(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
