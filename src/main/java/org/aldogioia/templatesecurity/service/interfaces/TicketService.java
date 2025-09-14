package org.aldogioia.templatesecurity.service.interfaces;

import org.aldogioia.templatesecurity.data.dto.creates.TicketCreateDto;
import org.aldogioia.templatesecurity.data.dto.responses.TicketDto;
import org.aldogioia.templatesecurity.data.dto.responses.TicketsPdfDto;
import org.aldogioia.templatesecurity.data.entities.User;

import java.util.List;

public interface TicketService {
    List<TicketDto> getAllTickets();
    TicketsPdfDto createTicket(List<TicketCreateDto> ticketCreateDtos, User user);
    TicketDto invalidateTicket(String id);
    void deleteTicket(String id);
}
