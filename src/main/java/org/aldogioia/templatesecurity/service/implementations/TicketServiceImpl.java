package org.aldogioia.templatesecurity.service.implementations;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.aldogioia.templatesecurity.data.dao.ExhibitionPriceDao;
import org.aldogioia.templatesecurity.data.dao.TicketDao;
import org.aldogioia.templatesecurity.data.dao.UserDao;
import org.aldogioia.templatesecurity.data.dto.creates.TicketCreateDto;
import org.aldogioia.templatesecurity.data.dto.responses.TicketDto;
import org.aldogioia.templatesecurity.data.entities.ExhibitionPrice;
import org.aldogioia.templatesecurity.data.entities.Ticket;
import org.aldogioia.templatesecurity.data.entities.User;
import org.aldogioia.templatesecurity.data.enumerators.TicketStatus;
import org.aldogioia.templatesecurity.security.exception.customException.EntityNotFoundException;
import org.aldogioia.templatesecurity.security.exception.customException.TicketExpiredException;
import org.aldogioia.templatesecurity.service.interfaces.EmailService;
import org.aldogioia.templatesecurity.service.interfaces.TicketService;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final UserDao userDao;
    private final TicketDao ticketDao;
    private final ExhibitionPriceDao exhibitionPriceDao;
    private final EmailService emailService;
    private final ModelMapper modelMapper;

    @Override
    public List<TicketDto> getAllTickets() {
        return ticketDao.findAll()
                .stream()
                .map(ticket -> modelMapper.map(ticket, TicketDto.class))
                .toList();
    }

    @Override
    public TicketDto createTicket(TicketCreateDto ticketCreateDto) {
        ExhibitionPrice exhibitionPrice = exhibitionPriceDao.findById(ticketCreateDto.getExhibitionPriceId())
                .orElseThrow(() -> new EntityNotFoundException("Prezzo della mostra non trovato"));

        User user = userDao.findById(ticketCreateDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Utente non trovato"));

        Ticket ticket = modelMapper.map(ticketCreateDto, Ticket.class);
        ticket.setPriceAtPurchase(exhibitionPrice.getPrice());
        ticket.setExhibitionPrice(exhibitionPrice);
        ticket.setUser(user);

        Ticket savedTicket = ticketDao.save(ticket);

        emailService.sendTicketEmail(user.getEmail(), ticket);

        return modelMapper.map(savedTicket, TicketDto.class);
    }

    @Override
    public void invalidateTicket(String id) {
        Ticket ticket = ticketDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Biglietto non trovato"));

        if (ticket.getStatus().name().equals(TicketStatus.EXPIRED.name())) {
            throw new TicketExpiredException("Il biglietto è già scaduto e non può essere invalidato.");
        } else {
            ticket.setStatus(TicketStatus.INVALID);
            ticketDao.save(ticket);
        }
    }

    @Override
    public void deleteTicket(String id) {
        Ticket ticket = ticketDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Biglietto non trovato"));

        ticketDao.delete(ticket);
    }

    @Scheduled(cron = "0 0 3 * * *")
    @Transactional
    public void updateTicketStatus() {
        ticketDao.findByExhibitionPrice_Exhibition_EndDateBeforeAndStatus(LocalDate.now(), TicketStatus.VALID)
                .forEach(ticket -> ticket.setStatus(TicketStatus.EXPIRED));
    }
}
