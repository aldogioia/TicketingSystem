package org.aldogioia.templatesecurity.controller;

import lombok.RequiredArgsConstructor;
import org.aldogioia.templatesecurity.security.availability.RateLimit;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RateLimit(permitsPerSecond = 10)
@RequestMapping("/api/v1/ad-banner")
public class AdBannerController {
}
