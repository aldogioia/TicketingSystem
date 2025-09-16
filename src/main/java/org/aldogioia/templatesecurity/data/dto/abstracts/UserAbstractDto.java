package org.aldogioia.templatesecurity.data.dto.abstracts;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.aldogioia.templatesecurity.security.customAnnotation.ValidEmail;
import org.aldogioia.templatesecurity.security.customAnnotation.ValidPhoneNumber;

@Data
public class UserAbstractDto {
    @NotBlank(message = "Il nome non può essere vuoto")
    @Size(min = 3, max = 50, message = "Il nome deve essere compreso tra 3 e 50 caratteri")
    private String name;

    @NotBlank(message = "Il cognome non può essere vuoto")
    @Size(min = 3, max = 50, message = "Il cognome deve essere compreso tra 3 e 50 caratteri")
    private String surname;

    @NotBlank(message = "L'email non può essere vuota")
    @Email(message = "L'email deve essere valida")
    @ValidEmail
    private String email;

    @NotBlank(message = "Il numero di telefono non può essere vuoto")
    @Pattern(regexp = "^[0-9]{10}$", message = "Il numero di telefono deve essere un numero di 10 cifre")
    @ValidPhoneNumber
    private String phoneNumber;

    @NotBlank(message = "La password non può essere vuota")
    @Pattern(regexp = "^(?=.*[!@#])(?=.*\\d).{8,}$", message = "La password deve avere almeno 8 caratteri, almeno un numero e almeno uno tra i caratteri speciali !@#")
    private String password;
}
