package org.aldogioia.templatesecurity.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aldogioia.templatesecurity.data.dto.creates.ExhibitionCreateDto;
import org.aldogioia.templatesecurity.data.dto.responses.ExhibitionDto;
import org.aldogioia.templatesecurity.data.dto.updates.ExhibitionUpdateDto;
import org.aldogioia.templatesecurity.security.availability.RateLimit;
import org.aldogioia.templatesecurity.service.interfaces.ExhibitionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RateLimit(permitsPerSecond = 10)
@RequestMapping("/api/v1/exhibition")
public class ExhibitionController {
    private final ExhibitionService exhibitionService;

    @GetMapping("/get-all")
    public ResponseEntity<List<ExhibitionDto>> getAllExhibitions() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(exhibitionService.getExhibitions());
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ExhibitionDto> createExhibition(@Valid @RequestBody ExhibitionCreateDto exhibitionCreateDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(exhibitionService.createExhibition(exhibitionCreateDto));
    }

    @PatchMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ExhibitionDto> updateExhibition(@Valid @RequestBody ExhibitionUpdateDto exhibitionUpdateDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(exhibitionService.updateExhibition(exhibitionUpdateDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<HttpStatus> deleteExhibition(@PathVariable String id) {
        exhibitionService.deleteExhibition(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
