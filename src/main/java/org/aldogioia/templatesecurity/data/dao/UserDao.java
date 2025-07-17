package org.aldogioia.templatesecurity.data.dao;

import org.aldogioia.templatesecurity.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interfaccia DAO per l'entità {@link User}.
 * <p>
 * Estende {@link JpaRepository} fornendo metodi CRUD e query personalizzate
 * per la gestione degli utenti nel database.
 */
@Repository
public interface UserDao extends JpaRepository<User, String> {
    /**
     * Recupera un utente tramite l'indirizzo email.
     *
     * @param email l'indirizzo email dell'utente
     * @return l'utente corrispondente, oppure {@code null} se non trovato
     */
    Optional<User> findByEmail(String email);

    /**
     * Recupera un utente tramite il numero di telefono.
     *
     * @param phoneNumber il numero di telefono dell'utente
     * @return l'utente corrispondente, oppure {@code null} se non trovato
     */
    Optional<User> findByPhoneNumber(String phoneNumber);
}
