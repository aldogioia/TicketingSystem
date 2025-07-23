package org.aldogioia.templatesecurity.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aldogioia.templatesecurity.data.dto.creates.TicketTypeCreateDto;
import org.aldogioia.templatesecurity.data.dto.responses.TicketTypeDto;
import org.aldogioia.templatesecurity.data.dto.updates.TicketTypeUpdateDto;
import org.aldogioia.templatesecurity.security.availability.RateLimit;
import org.aldogioia.templatesecurity.service.interfaces.TicketTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RateLimit(permitsPerSecond = 10)
@RequestMapping("/api/v1/ticket-type")
public class TicketTypeController {
    private final TicketTypeService ticketTypeService;

    @GetMapping("/get-all")
    public ResponseEntity<List<TicketTypeDto>> getAllTicketTypes() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ticketTypeService.getAllTicketTypes());
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<TicketTypeDto> createTicketType(@Valid @RequestBody TicketTypeCreateDto ticketTypeCreateDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ticketTypeService.createTicketType(ticketTypeCreateDto));
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<TicketTypeDto> updateTicketType(@Valid @RequestBody TicketTypeUpdateDto ticketTypeUpdateDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ticketTypeService.updateTicketType(ticketTypeUpdateDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<HttpStatus> deleteTicketType(@PathVariable String id) {
        ticketTypeService.deleteTicketType(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
