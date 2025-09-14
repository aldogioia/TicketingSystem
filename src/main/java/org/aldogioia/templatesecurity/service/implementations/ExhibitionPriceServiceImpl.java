package org.aldogioia.templatesecurity.service.implementations;

import lombok.RequiredArgsConstructor;
import org.aldogioia.templatesecurity.data.dao.ExhibitionDao;
import org.aldogioia.templatesecurity.data.dao.ExhibitionPriceDao;
import org.aldogioia.templatesecurity.data.dao.TicketTypeDao;
import org.aldogioia.templatesecurity.data.dto.creates.ExhibitionPriceCreateDto;
import org.aldogioia.templatesecurity.data.dto.responses.ExhibitionPriceDto;
import org.aldogioia.templatesecurity.data.dto.responses.ExhibitionPriceWithTicketTypeDto;
import org.aldogioia.templatesecurity.data.dto.responses.TicketTypeDto;
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
    public List<ExhibitionPriceWithTicketTypeDto> getAllExhibitionPricesWithTicketType() {
        return exhibitionPriceDao.findAll()
                .stream()
                .map(exhibitionPrice -> {
                    ExhibitionPriceWithTicketTypeDto dto = new ExhibitionPriceWithTicketTypeDto();
                    dto.setExhibitionPrice(modelMapper.map(exhibitionPrice, ExhibitionPriceDto.class));
                    dto.setTicketType(modelMapper.map(exhibitionPrice.getTicketType(), TicketTypeDto.class));
                    return dto;
                })
                .toList();
    }

    @Override
    public void createExhibitionPrice(List<ExhibitionPriceCreateDto> exhibitionPriceCreateDtos) {
        Exhibition exhibition = exhibitionDao.findById(exhibitionPriceCreateDtos.get(0).getExhibitionId())
                .orElseThrow(() -> new RuntimeException("Mostra non trovata"));

        List<ExhibitionPrice> exhibitionPrices = exhibitionPriceCreateDtos.stream()
                .map(dto -> {
                    TicketType ticketType = ticketTypeDao.findById(dto.getTicketTypeId())
                            .orElseThrow(() -> new RuntimeException("Tipo di biglietto non trovato"));

                    ExhibitionPrice exhibitionPrice = modelMapper.map(dto, ExhibitionPrice.class);
                    exhibitionPrice.setExhibition(exhibition);
                    exhibitionPrice.setTicketType(ticketType);
                    return exhibitionPrice;
                })
                .toList();

        exhibitionPriceDao.saveAll(exhibitionPrices);
    }

    @Override
    public void updateExhibitionPrice(List<ExhibitionPriceUpdateDto> exhibitionPriceUpdateDtos) {
        Exhibition exhibition = exhibitionDao.findById(exhibitionPriceUpdateDtos.get(0).getExhibitionId())
                .orElseThrow(() -> new RuntimeException("Mostra non trovata"));

        List<ExhibitionPrice> exhibitionPrices = exhibitionPriceUpdateDtos.stream()
                .map(dto -> {
                    ExhibitionPrice exhibitionPrice = exhibitionPriceDao.findById(dto.getId())
                            .orElseThrow(() -> new RuntimeException("Prezzo della mostra non trovato"));

                    TicketType ticketType = ticketTypeDao.findById(dto.getTicketTypeId())
                            .orElseThrow(() -> new RuntimeException("Tipo di biglietto non trovato"));

                    modelMapper.map(dto, exhibitionPrice);
                    exhibitionPrice.setExhibition(exhibition);
                    exhibitionPrice.setTicketType(ticketType);
                    return exhibitionPrice;
                })
                .toList();

        exhibitionPriceDao.saveAll(exhibitionPrices);
    }


    @Override
    public void deleteExhibitionPrice(List<String> ids) {
        List<ExhibitionPrice> exhibitionPrices = exhibitionPriceDao.findAllById(ids);
        exhibitionPriceDao.deleteAll(exhibitionPrices);
    }
}
