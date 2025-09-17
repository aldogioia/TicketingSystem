package org.aldogioia.templatesecurity.security.customAnnotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.aldogioia.templatesecurity.data.dao.TicketTypeDao;
import org.aldogioia.templatesecurity.data.dto.abstracts.TicketTypeAbstractDto;
import org.aldogioia.templatesecurity.data.dto.creates.TicketTypeCreateDto;
import org.aldogioia.templatesecurity.data.dto.updates.TicketTypeUpdateDto;

@RequiredArgsConstructor
public class TicketTypeNameValidator implements ConstraintValidator<ValidTicketTypeName, TicketTypeAbstractDto> {

    private final TicketTypeDao ticketTypeDao;

    @Override
    public boolean isValid(TicketTypeAbstractDto dto, ConstraintValidatorContext context) {
        if (dto == null || dto.getName() == null) {
            return true;
        }

        String name = dto.getName().trim();
        boolean valid = true;

        if (dto instanceof TicketTypeCreateDto) {
            valid = !ticketTypeDao.existsByNameIgnoreCase(name);
        } else if (dto instanceof TicketTypeUpdateDto updateDto) {
            valid = !ticketTypeDao.existsByNameIgnoreCaseAndIdNot(name, updateDto.getId());
        }

        if (!valid) {
            context.disableDefaultConstraintViolation();

            context.buildConstraintViolationWithTemplate("Il nome '" + name + "' è già in uso")
                    .addPropertyNode("name")
                    .addConstraintViolation();
        }

        return valid;
    }
}
