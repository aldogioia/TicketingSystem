package org.aldogioia.templatesecurity.security.customAnnotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.aldogioia.templatesecurity.data.dao.UserDao;

@RequiredArgsConstructor
public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {
    private final UserDao userDao;

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        return userDao.findByPhoneNumber(phoneNumber).isEmpty();
    }
}
