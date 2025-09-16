package org.aldogioia.templatesecurity.controller;

import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.aldogioia.templatesecurity.security.availability.RateLimit;
import org.aldogioia.templatesecurity.service.interfaces.NewsletterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RateLimit(permitsPerSecond = 10)
@RequestMapping("/api/newsletter")
public class NewsletterController {
    private final NewsletterService newsletterService;

    @PostMapping("/subscribe")
    public ResponseEntity<HttpStatus> subscribeToNewsletter(
            @Email @RequestParam String email
    ) {
        newsletterService.subscribeToNewsletter(email);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<HttpStatus> unsubscribeFromNewsletter(
            @Email @RequestParam String email
    ) {
        newsletterService.unsubscribeFromNewsletter(email);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
