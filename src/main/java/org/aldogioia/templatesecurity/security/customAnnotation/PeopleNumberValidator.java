package org.aldogioia.templatesecurity.security.customAnnotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.aldogioia.templatesecurity.data.dao.ExhibitionPriceDao;
import org.aldogioia.templatesecurity.data.dto.creates.TicketCreateDto;
import org.aldogioia.templatesecurity.data.entities.ExhibitionPrice;

import java.util.Optional;

@RequiredArgsConstructor
public class PeopleNumberValidator implements ConstraintValidator<ValidPeopleNumber, TicketCreateDto> {
    private final ExhibitionPriceDao exhibitionPriceDao;

    @Override
    public boolean isValid(TicketCreateDto ticketCreateDto, ConstraintValidatorContext constraintValidatorContext) {
        Optional<ExhibitionPrice> exhibitionPrice = exhibitionPriceDao.findById(ticketCreateDto.getExhibitionPriceId());

        if(exhibitionPrice.isPresent()){
            if(exhibitionPrice.get().getTicketType().getIsMultipleEntryAllowed()){
                return ticketCreateDto.getPeopleNumber() != null && ticketCreateDto.getPeopleNumber() > 1;
            } else {
                return ticketCreateDto.getPeopleNumber() == null;
            }
        }

        return false;
    }
}
