package org.aldogioia.templatesecurity.data.dto.abstracts;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public abstract class TicketAbstractDto {
    @NotBlank(message = "L'ID del prezzo dell'esibizione è obbligatorio")
    private String exhibitionPriceId;

    @NotBlank(message = "L'ID dell'utente è obbligatorio")
    private String userId;
}
