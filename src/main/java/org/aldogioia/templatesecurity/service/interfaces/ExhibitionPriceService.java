package org.aldogioia.templatesecurity.service.interfaces;

import org.aldogioia.templatesecurity.data.dto.creates.ExhibitionPriceCreateDto;
import org.aldogioia.templatesecurity.data.dto.responses.ExhibitionPriceDto;
import org.aldogioia.templatesecurity.data.dto.updates.ExhibitionPriceUpdateDto;

import java.util.List;

public interface ExhibitionPriceService {
     List<ExhibitionPriceDto> getAllExhibitionPrices();
     ExhibitionPriceDto createExhibitionPrice(ExhibitionPriceCreateDto exhibitionPriceCreateDto);
     ExhibitionPriceDto updateExhibitionPrice(ExhibitionPriceUpdateDto exhibitionPriceUpdateDto);
     void deleteExhibitionPrice(String id);
}
