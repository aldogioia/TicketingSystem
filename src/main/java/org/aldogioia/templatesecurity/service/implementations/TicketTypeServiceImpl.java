package org.aldogioia.templatesecurity.service.implementations;

import lombok.RequiredArgsConstructor;
import org.aldogioia.templatesecurity.data.dao.TicketTypeDao;
import org.aldogioia.templatesecurity.data.dto.creates.TicketTypeCreateDto;
import org.aldogioia.templatesecurity.data.dto.responses.TicketTypeDto;
import org.aldogioia.templatesecurity.data.dto.updates.TicketTypeUpdateDto;
import org.aldogioia.templatesecurity.data.entities.TicketType;
import org.aldogioia.templatesecurity.service.interfaces.TicketTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketTypeServiceImpl implements TicketTypeService {
    private final TicketTypeDao ticketTypeDao;
    private final ModelMapper modelMapper;

    @Override
    public List<TicketTypeDto> getAllTicketTypes() {
        return ticketTypeDao.findAll()
                .stream()
                .map(ticketType -> modelMapper.map(ticketType, TicketTypeDto.class))
                .toList();
    }

    @Override
    public TicketTypeDto createTicketType(TicketTypeCreateDto ticketTypeCreateDto) {
        TicketType ticketType = modelMapper.map(ticketTypeCreateDto, TicketType.class);
        TicketType savedTicketType = ticketTypeDao.save(ticketType);

        return modelMapper.map(savedTicketType, TicketTypeDto.class);
    }

    @Override
    public TicketTypeDto updateTicketType(TicketTypeUpdateDto ticketTypeUpdateDto) {
        TicketType existingTicketType = ticketTypeDao.findById(ticketTypeUpdateDto.getId())
                .orElseThrow(() -> new RuntimeException("Tipo di biglietto non trovato"));

        modelMapper.map(ticketTypeUpdateDto, existingTicketType);
        TicketType updatedTicketType = ticketTypeDao.save(existingTicketType);

        return modelMapper.map(updatedTicketType, TicketTypeDto.class);
    }

    @Override
    public void deleteTicketType(String id) {
        TicketType ticketType = ticketTypeDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo di biglietto non trovato"));

        ticketTypeDao.delete(ticketType);
    }
}
