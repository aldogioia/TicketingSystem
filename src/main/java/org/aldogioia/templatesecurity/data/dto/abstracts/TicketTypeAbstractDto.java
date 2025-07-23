package org.aldogioia.templatesecurity.data.dto.abstracts;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.aldogioia.templatesecurity.security.customAnnotation.ValidTicketTypeName;

@Data
public abstract class TicketTypeAbstractDto {
    @NotBlank(message = "Il nome è obbligatorio")
    @Size(min = 1, max = 50, message = "Il nome dev'essere compreso tra 1 e 50 caratteri")
    @ValidTicketTypeName
    private String name;

    @NotBlank(message = "La descrizione è obbligatoria")
    @Size(min = 1, message = "La descrizione dev'essere lunga almeno 1 carattere")
    private String description;
}
