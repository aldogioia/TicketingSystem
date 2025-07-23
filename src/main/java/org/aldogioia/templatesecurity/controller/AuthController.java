package org.aldogioia.templatesecurity.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.aldogioia.templatesecurity.data.dto.responses.AuthResponseDto;
import org.aldogioia.templatesecurity.data.dto.creates.CustomerCreateDto;
import org.aldogioia.templatesecurity.security.availability.RateLimit;
import org.aldogioia.templatesecurity.service.interfaces.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RateLimit(permitsPerSecond = 10)
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponseDto> signIn(
            @NotBlank(message = "L'email è obbligatoria") @RequestParam String email,
            @NotBlank(message = "La password è obbligatoria") @RequestParam String password
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.signIn(email, password));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<HttpStatus> signUp(@Valid @RequestBody CustomerCreateDto customerCreateDto) {
        authService.signUp(customerCreateDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refreshToken(HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.refresh(request));
    }

    @GetMapping("/sign-out")
    public ResponseEntity<HttpStatus> signOut(HttpServletRequest request) {
        authService.signOut(request);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
