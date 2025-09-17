package org.aldogioia.templatesecurity.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpaConfiguration implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Qualsiasi richiesta che non contiene un punto (file statico)
        registry.addViewController("/{spring:[^\\.]+}")
                .setViewName("forward:/");
        registry.addViewController("/**/{spring:[^\\.]+}")
                .setViewName("forward:/");
    }
}