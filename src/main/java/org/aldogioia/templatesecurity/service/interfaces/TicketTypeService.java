package org.aldogioia.templatesecurity.service.interfaces;

import org.aldogioia.templatesecurity.data.dto.creates.TicketTypeCreateDto;
import org.aldogioia.templatesecurity.data.dto.responses.TicketTypeDto;
import org.aldogioia.templatesecurity.data.dto.updates.TicketTypeUpdateDto;

import java.util.List;

public interface TicketTypeService {
     List<TicketTypeDto> getAllTicketTypes();
     TicketTypeDto createTicketType(TicketTypeCreateDto ticketTypeCreateDto);
     TicketTypeDto updateTicketType(TicketTypeUpdateDto ticketTypeUpdateDto);
     void deleteTicketType(String id);
}
