package org.aldogioia.templatesecurity.data.dao;

import org.aldogioia.templatesecurity.data.entities.PasswordCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PasswordCodeDao extends JpaRepository<PasswordCode, String> {
    void deleteAllByExpiresAtIsBefore(LocalDateTime time);
    Optional<PasswordCode> findByEmailAndCode(String email, String code);
}
