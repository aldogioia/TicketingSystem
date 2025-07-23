package org.aldogioia.templatesecurity.data.dao;

import org.aldogioia.templatesecurity.data.entities.Ticket;
import org.aldogioia.templatesecurity.data.enumerators.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TicketDao extends JpaRepository<Ticket, String> {
    List<Ticket> findByExhibitionPrice_Exhibition_EndDateBeforeAndStatus(LocalDate endDate, TicketStatus status);
}
