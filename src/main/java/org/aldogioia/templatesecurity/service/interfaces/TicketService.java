package org.aldogioia.templatesecurity.service.interfaces;

import org.aldogioia.templatesecurity.data.dto.creates.TicketCreateDto;
import org.aldogioia.templatesecurity.data.dto.responses.TicketDto;

import java.util.List;

public interface TicketService {
    List<TicketDto> getAllTickets();
    TicketDto createTicket(TicketCreateDto ticketCreateDto);
    void invalidateTicket(String id);
    void deleteTicket(String id);
}
