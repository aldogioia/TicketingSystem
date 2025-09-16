package org.aldogioia.templatesecurity.service.interfaces;

import org.aldogioia.templatesecurity.data.dto.creates.ValidatorCreateDto;

public interface ValidatorService {
    void createValidator(ValidatorCreateDto validatorCreateDto);
}
