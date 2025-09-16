package org.aldogioia.templatesecurity.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aldogioia.templatesecurity.data.dto.creates.ValidatorCreateDto;
import org.aldogioia.templatesecurity.security.availability.RateLimit;
import org.aldogioia.templatesecurity.service.interfaces.ValidatorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RateLimit(permitsPerSecond = 10)
@RequestMapping("/api/v1/validator")
public class ValidatorController {
    private final ValidatorService validatorService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<HttpStatus> signUp(@Valid @RequestBody ValidatorCreateDto validatorCreateDto) {
        validatorService.createValidator(validatorCreateDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
