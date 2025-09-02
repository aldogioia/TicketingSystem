package org.aldogioia.templatesecurity.data.dao;

import org.aldogioia.templatesecurity.data.entities.Newsletter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewsletterDao extends JpaRepository<Newsletter, String> {
    Optional<Newsletter> findByEmail(String email);
    boolean existsByEmail(String email);
}
