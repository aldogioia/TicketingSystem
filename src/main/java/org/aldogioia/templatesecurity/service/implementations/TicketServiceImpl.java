package org.aldogioia.templatesecurity.service.implementations;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.aldogioia.templatesecurity.data.dao.ExhibitionPriceDao;
import org.aldogioia.templatesecurity.data.dao.TicketDao;
import org.aldogioia.templatesecurity.data.dto.creates.TicketCreateDto;
import org.aldogioia.templatesecurity.data.dto.responses.TicketDto;
import org.aldogioia.templatesecurity.data.dto.responses.TicketsPdfDto;
import org.aldogioia.templatesecurity.data.entities.ExhibitionPrice;
import org.aldogioia.templatesecurity.data.entities.Ticket;
import org.aldogioia.templatesecurity.data.entities.User;
import org.aldogioia.templatesecurity.data.enumerators.TicketStatus;
import org.aldogioia.templatesecurity.security.exception.customException.EmailException;
import org.aldogioia.templatesecurity.security.exception.customException.EntityNotFoundException;
import org.aldogioia.templatesecurity.security.exception.customException.TicketExpiredException;
import org.aldogioia.templatesecurity.security.exception.customException.TicketInvalidException;
import org.aldogioia.templatesecurity.service.interfaces.TicketService;
import org.aldogioia.templatesecurity.utils.PdfGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketDao ticketDao;
    private final ExhibitionPriceDao exhibitionPriceDao;
    private final PdfGenerator pdfGenerator;
    private final ModelMapper modelMapper;

    @Override
    public List<TicketDto> getAllTickets() {
        return ticketDao.findAll()
                .stream()
                .map(ticket -> modelMapper.map(ticket, TicketDto.class))
                .toList();
    }

    @Override
    public TicketsPdfDto createTicket(List<TicketCreateDto> ticketCreateDtos, User user) {
        List<ExhibitionPrice> exhibitionPrices = exhibitionPriceDao.findAllById(ticketCreateDtos.stream()
                .map(TicketCreateDto::getExhibitionPriceId)
                .toList());

        List<Ticket> tickets = ticketCreateDtos.stream()
                .flatMap(ticketDto -> {
                    ExhibitionPrice exhibitionPrice = exhibitionPrices.stream()
                            .filter(price -> price.getId().equals(ticketDto.getExhibitionPriceId()))
                            .findFirst()
                            .orElseThrow(() -> new EntityNotFoundException("Prezzo per la mostra non trovato"));

                    return java.util.stream.IntStream.range(0, ticketDto.getQuantity())
                            .mapToObj(i -> {
                                Ticket newTicket = modelMapper.map(ticketDto, Ticket.class);
                                newTicket.setExhibitionPrice(exhibitionPrice);
                                newTicket.setPriceAtPurchase(exhibitionPrice.getPrice());
                                newTicket.setExhibitionTitle(exhibitionPrice.getExhibition().getTitle());
                                newTicket.setTicketType(exhibitionPrice.getTicketType().getName());
                                newTicket.setUser(user);
                                return newTicket;
                            });
                })
                .toList();

        List<Ticket> savedTickets = ticketDao.saveAll(tickets);

        TicketsPdfDto ticketsPdfDto = new TicketsPdfDto();

        try {
            ticketsPdfDto.setPdf(pdfGenerator.generatePdfTickets(tickets));
        } catch (Exception e) {
            throw new EmailException("Errore durante la generazione dei biglietti. Riprovare Grazie.");
        }

        ticketsPdfDto.setTickets(savedTickets.stream()
                .map(ticket -> modelMapper.map(ticket, TicketDto.class))
                .toList());

        return ticketsPdfDto;
    }

    @Override
    public TicketDto invalidateTicket(String id) {
        Ticket ticket = ticketDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Biglietto non trovato"));

        if (ticket.getStatus().name().equals(TicketStatus.EXPIRED.name())) {
            throw new TicketExpiredException("Il biglietto è già scaduto e non può essere invalidato.");
        }
        else if (ticket.getStatus().name().equals(TicketStatus.INVALID.name())) {
            throw new TicketInvalidException("Il biglietto è già stato invalidato.");
        } else {
            ticket.setStatus(TicketStatus.INVALID);
            ticketDao.save(ticket);

            return modelMapper.map(ticket, TicketDto.class);
        }
    }

    @Override
    public void deleteTicket(String id) {
        Ticket ticket = ticketDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Biglietto non trovato"));

        ticketDao.delete(ticket);
    }

    @Scheduled(cron = "0 0 9 * * *")
    @Transactional
    public void updateTicketStatus() {
        ticketDao.findByExhibitionPrice_Exhibition_EndDateBeforeAndStatus(LocalDate.now(), TicketStatus.VALID)
                .forEach(ticket -> ticket.setStatus(TicketStatus.EXPIRED));
    }
}
