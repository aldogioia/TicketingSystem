package org.aldogioia.templatesecurity.security.customAnnotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.aldogioia.templatesecurity.data.dao.TicketTypeDao;

@RequiredArgsConstructor
public class TicketTypeNameValidator  implements ConstraintValidator<ValidTicketTypeName, String> {
    private final TicketTypeDao ticketTypeDao;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return ticketTypeDao.findAll()
                .stream()
                .noneMatch(tt -> tt.getName().equalsIgnoreCase(value));
    }
}
