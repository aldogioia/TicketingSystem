package org.aldogioia.templatesecurity.data.dto.abstracts;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.aldogioia.templatesecurity.security.customAnnotation.ValidTicketTypeName;

@Data
@ValidTicketTypeName
public abstract class TicketTypeAbstractDto {
    @NotBlank(message = "Il nome è obbligatorio")
    @Size(min = 1, max = 50, message = "Il nome dev'essere compreso tra 1 e 50 caratteri")
    private String name;

    @NotNull(message = "È obbligatorio specificare se l'accesso multiplo è consentito")
    private Boolean isMultipleEntryAllowed;
}
