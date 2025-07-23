package org.aldogioia.templatesecurity.security.customAnnotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.aldogioia.templatesecurity.data.dao.TicketTypeDao;
import org.aldogioia.templatesecurity.data.dto.abstracts.TicketTypeAbstractDto;

@RequiredArgsConstructor
public class TicketTypeNameValidator  implements ConstraintValidator<ValidPhoneNumber, TicketTypeAbstractDto> {
    private final TicketTypeDao ticketTypeDao;
    @Override
    public boolean isValid(TicketTypeAbstractDto ticketType, ConstraintValidatorContext constraintValidatorContext) {
        return ticketTypeDao.findAll()
                .stream()
                .noneMatch(tt -> tt.getName().equalsIgnoreCase(ticketType.getName()));
    }
}
