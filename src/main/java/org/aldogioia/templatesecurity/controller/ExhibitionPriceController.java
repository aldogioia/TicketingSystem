package org.aldogioia.templatesecurity.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aldogioia.templatesecurity.data.dto.creates.ExhibitionPriceCreateDto;
import org.aldogioia.templatesecurity.data.dto.responses.ExhibitionPriceDto;
import org.aldogioia.templatesecurity.data.dto.responses.ExhibitionPriceWithTicketTypeDto;
import org.aldogioia.templatesecurity.data.dto.updates.ExhibitionPriceUpdateDto;
import org.aldogioia.templatesecurity.security.availability.RateLimit;
import org.aldogioia.templatesecurity.service.interfaces.ExhibitionPriceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RateLimit(permitsPerSecond = 10)
@RequestMapping("/api/v1/exhibition-price")
public class ExhibitionPriceController {
    private final ExhibitionPriceService exhibitionPriceService;

    @GetMapping("/get-all")
    public ResponseEntity<List<ExhibitionPriceDto>> getAllExhibitionPrice() {
        List<ExhibitionPriceDto> exhibitionPriceDtoList = exhibitionPriceService.getAllExhibitionPrices();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(exhibitionPriceDtoList);
    }

    @GetMapping("/get-all-with-ticket-type")
    public ResponseEntity<List<ExhibitionPriceWithTicketTypeDto>> getAllExhibitionPricesWithTicketType() {
        List<ExhibitionPriceWithTicketTypeDto> exhibitionPriceDtoList = exhibitionPriceService.getAllExhibitionPricesWithTicketType();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(exhibitionPriceDtoList);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ExhibitionPriceDto> createExhibitionPrice(@Valid @RequestBody List<ExhibitionPriceCreateDto> exhibitionPriceCreateDtos) {
        exhibitionPriceService.createExhibitionPrice(exhibitionPriceCreateDtos);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ExhibitionPriceDto> updateExhibitionPrice(@Valid @RequestBody List<ExhibitionPriceUpdateDto> exhibitionPriceUpdateDtos) {
        exhibitionPriceService.updateExhibitionPrice(exhibitionPriceUpdateDtos);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteExhibitionPrice(@RequestBody List<String> ids) {
        exhibitionPriceService.deleteExhibitionPrice(ids);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
