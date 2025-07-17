package org.aldogioia.templatesecurity.data.dao;

import org.aldogioia.templatesecurity.data.entities.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketTypeDao extends JpaRepository<TicketType, String> {
}
