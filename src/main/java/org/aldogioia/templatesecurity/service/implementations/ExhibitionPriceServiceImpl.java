package org.aldogioia.templatesecurity.service.implementations;

import lombok.RequiredArgsConstructor;
import org.aldogioia.templatesecurity.data.dao.ExhibitionDao;
import org.aldogioia.templatesecurity.data.dao.ExhibitionPriceDao;
import org.aldogioia.templatesecurity.data.dao.TicketTypeDao;
import org.aldogioia.templatesecurity.data.dto.creates.ExhibitionPriceCreateDto;
import org.aldogioia.templatesecurity.data.dto.responses.ExhibitionPriceDto;
import org.aldogioia.templatesecurity.data.dto.updates.ExhibitionPriceUpdateDto;
import org.aldogioia.templatesecurity.data.entities.Exhibition;
import org.aldogioia.templatesecurity.data.entities.ExhibitionPrice;
import org.aldogioia.templatesecurity.data.entities.TicketType;
import org.aldogioia.templatesecurity.service.interfaces.ExhibitionPriceService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExhibitionPriceServiceImpl implements ExhibitionPriceService {
    private final ExhibitionPriceDao exhibitionPriceDao;
    private final ExhibitionDao exhibitionDao;
    private final TicketTypeDao ticketTypeDao;
    private final ModelMapper modelMapper;

    @Override
    public List<ExhibitionPriceDto> getAllExhibitionPrices() {
        return exhibitionPriceDao.findAll()
                .stream()
                .map(exhibitionPrice -> modelMapper.map(exhibitionPrice, ExhibitionPriceDto.class))
                .toList();
    }

    @Override
    public ExhibitionPriceDto createExhibitionPrice(ExhibitionPriceCreateDto exhibitionPriceCreateDto) {
        Exhibition exhibition = exhibitionDao.findById(exhibitionPriceCreateDto.getExhibitionId())
                .orElseThrow(() -> new RuntimeException("Mostra non trovata"));

        TicketType ticketType = ticketTypeDao.findById(exhibitionPriceCreateDto.getTicketTypeId())
                .orElseThrow(() -> new RuntimeException("Tipo di biglietto non trovato"));

        ExhibitionPrice exhibitionPrice = modelMapper.map(exhibitionPriceCreateDto, ExhibitionPrice.class);
        exhibitionPrice.setExhibition(exhibition);
        exhibitionPrice.setTicketType(ticketType);

        ExhibitionPrice savedExhibitionPrice = exhibitionPriceDao.save(exhibitionPrice);

        return modelMapper.map(savedExhibitionPrice, ExhibitionPriceDto.class);
    }

    @Override
    public ExhibitionPriceDto updateExhibitionPrice(ExhibitionPriceUpdateDto exhibitionPriceUpdateDto) {
        ExhibitionPrice exhibitionPrice = exhibitionPriceDao.findById(exhibitionPriceUpdateDto.getId())
                .orElseThrow(() -> new RuntimeException("Prezzo della mostra non trovato"));

        Exhibition exhibition = exhibitionDao.findById(exhibitionPriceUpdateDto.getExhibitionId())
                .orElseThrow(() -> new RuntimeException("Mostra non trovata"));
        exhibitionPrice.setExhibition(exhibition);

        TicketType ticketType = ticketTypeDao.findById(exhibitionPriceUpdateDto.getTicketTypeId())
                .orElseThrow(() -> new RuntimeException("Tipo di biglietto non trovato"));
        exhibitionPrice.setTicketType(ticketType);

        modelMapper.map(exhibitionPriceUpdateDto, exhibitionPrice);

        ExhibitionPrice updatedExhibitionPrice = exhibitionPriceDao.save(exhibitionPrice);

        return modelMapper.map(updatedExhibitionPrice, ExhibitionPriceDto.class);
    }

    @Override
    public void deleteExhibitionPrice(String id) {
        ExhibitionPrice exhibitionPrice = exhibitionPriceDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Prezzo della mostra non trovato"));

        exhibitionPriceDao.delete(exhibitionPrice);
    }
}
