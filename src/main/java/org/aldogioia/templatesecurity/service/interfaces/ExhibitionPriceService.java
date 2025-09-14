package org.aldogioia.templatesecurity.service.interfaces;

import org.aldogioia.templatesecurity.data.dto.creates.ExhibitionPriceCreateDto;
import org.aldogioia.templatesecurity.data.dto.responses.ExhibitionPriceDto;
import org.aldogioia.templatesecurity.data.dto.responses.ExhibitionPriceWithTicketTypeDto;
import org.aldogioia.templatesecurity.data.dto.updates.ExhibitionPriceUpdateDto;

import java.util.List;

public interface ExhibitionPriceService {
    List<ExhibitionPriceDto> getAllExhibitionPrices();
    List<ExhibitionPriceWithTicketTypeDto> getAllExhibitionPricesWithTicketType();
     void createExhibitionPrice(List<ExhibitionPriceCreateDto> exhibitionPriceCreateDtos);
     void updateExhibitionPrice(List<ExhibitionPriceUpdateDto> exhibitionPriceUpdateDtos);
     void deleteExhibitionPrice(List<String> ids);
}
