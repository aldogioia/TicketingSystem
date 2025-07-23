package org.aldogioia.templatesecurity.security.customAnnotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = TicketTypeNameValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTicketTypeName {
    String message() default "Il nome del tipo di biglietto è già in uso";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
