package org.aldogioia.templatesecurity.security.customAnnotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PeopleNumberValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPeopleNumber {
    String message() default "Il numero di persone deve essere valido in base alla tipologia di biglietto";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
