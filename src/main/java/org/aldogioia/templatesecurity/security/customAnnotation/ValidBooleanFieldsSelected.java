package org.aldogioia.templatesecurity.security.customAnnotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = BooleanFieldsSelectedValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidBooleanFieldsSelected {
    String message() default "Il Valore dei campi booleani non è valido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}