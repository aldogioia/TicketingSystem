package org.aldogioia.templatesecurity.service.implementations;

import lombok.RequiredArgsConstructor;
import org.aldogioia.templatesecurity.data.dao.ValidatorDao;
import org.aldogioia.templatesecurity.data.dto.creates.ValidatorCreateDto;
import org.aldogioia.templatesecurity.data.entities.Validator;
import org.aldogioia.templatesecurity.service.interfaces.ValidatorService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidatorServiceImpl implements ValidatorService {
    private final ValidatorDao validatorDao;
    private final ModelMapper modelMapper;

    @Override
    public void createValidator(ValidatorCreateDto validatorCreateDto) {
        Validator validator = modelMapper.map(validatorCreateDto, Validator.class);
        validatorDao.save(validator);
    }
}
