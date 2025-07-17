package org.aldogioia.templatesecurity.data.dao;

import org.aldogioia.templatesecurity.data.entities.TicketPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketPriceDao extends JpaRepository<TicketPrice, String> {
}
