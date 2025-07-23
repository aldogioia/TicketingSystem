package org.aldogioia.templatesecurity.service.implementations;

import lombok.RequiredArgsConstructor;
import org.aldogioia.templatesecurity.data.dao.ExhibitionDao;
import org.aldogioia.templatesecurity.data.dto.creates.ExhibitionCreateDto;
import org.aldogioia.templatesecurity.data.dto.responses.ExhibitionDto;
import org.aldogioia.templatesecurity.data.dto.updates.ExhibitionUpdateDto;
import org.aldogioia.templatesecurity.data.entities.Exhibition;
import org.aldogioia.templatesecurity.service.interfaces.ExhibitionService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExhibitionServiceImpl implements ExhibitionService {
    private final ExhibitionDao exhibitionDao;
    private final ModelMapper modelMapper;

    @Override
    public List<ExhibitionDto> getExhibitions() {
        return exhibitionDao.findAll()
                .stream()
                .map(exhibition -> modelMapper.map(exhibition, ExhibitionDto.class))
                .toList();
    }

    @Override
    public ExhibitionDto createExhibition(ExhibitionCreateDto exhibitionCreateDto) {
        Exhibition exhibition = modelMapper.map(exhibitionCreateDto, Exhibition.class);
        Exhibition savedExhibition = exhibitionDao.save(exhibition);

        return modelMapper.map(savedExhibition, ExhibitionDto.class);
    }

    @Override
    public ExhibitionDto updateExhibition(ExhibitionUpdateDto exhibitionUpdateDto) {
        Exhibition exhibition = exhibitionDao.findById(exhibitionUpdateDto.getId())
                .orElseThrow(() -> new RuntimeException("Mostra non trovata"));

        modelMapper.map(exhibitionUpdateDto, exhibition);
        Exhibition updatedExhibition = exhibitionDao.save(exhibition);

        return modelMapper.map(updatedExhibition, ExhibitionDto.class);
    }

    @Override
    public void deleteExhibition(String exhibitionId) {
        Exhibition exhibition = exhibitionDao.findById(exhibitionId)
                .orElseThrow(() -> new RuntimeException("Mostra non trovata"));

        exhibitionDao.delete(exhibition);
    }
}
