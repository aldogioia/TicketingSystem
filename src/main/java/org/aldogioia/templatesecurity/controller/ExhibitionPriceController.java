package org.aldogioia.templatesecurity.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aldogioia.templatesecurity.data.dto.creates.ExhibitionPriceCreateDto;
import org.aldogioia.templatesecurity.data.dto.responses.ExhibitionPriceDto;
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

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ExhibitionPriceDto> createExhibitionPrice(@Valid @RequestBody ExhibitionPriceCreateDto exhibitionPriceCreateDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(exhibitionPriceService.createExhibitionPrice(exhibitionPriceCreateDto));
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ExhibitionPriceDto> updateExhibitionPrice(@Valid @RequestBody ExhibitionPriceUpdateDto exhibitionPriceUpdateDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(exhibitionPriceService.updateExhibitionPrice(exhibitionPriceUpdateDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteExhibitionPrice(@PathVariable String id) {
        exhibitionPriceService.deleteExhibitionPrice(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
