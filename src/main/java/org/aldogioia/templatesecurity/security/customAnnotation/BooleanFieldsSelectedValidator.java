package org.aldogioia.templatesecurity.security.customAnnotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.aldogioia.templatesecurity.data.dto.abstracts.TicketTypeAbstractDto;

public class BooleanFieldsSelectedValidator implements ConstraintValidator<ValidBooleanFieldsSelected, TicketTypeAbstractDto> {
    @Override
    public boolean isValid(TicketTypeAbstractDto ticketTypeAbstractDto, ConstraintValidatorContext constraintValidatorContext) {
        if (ticketTypeAbstractDto == null ) {
            return true;
        }

        if (!ticketTypeAbstractDto.getIsMultipleEntryAllowed() && ticketTypeAbstractDto.getIsPricePerPerson()) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("'Prezzo per persone' non può essere selezionato se 'Ingresso multiplo' non è selezionato")
                    .addPropertyNode("isPricePerPerson")
                    .addConstraintViolation();
            return false;
        } else {
            return true;
        }
    }
}
