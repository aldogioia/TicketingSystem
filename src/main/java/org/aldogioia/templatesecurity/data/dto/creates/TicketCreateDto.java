package org.aldogioia.templatesecurity.data.dto.creates;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.aldogioia.templatesecurity.security.customAnnotation.ValidPeopleNumber;

@Data
@ValidPeopleNumber
public class TicketCreateDto{
    @NotBlank(message = "L'ID del prezzo dell'esibizione è obbligatorio")
    private String exhibitionPriceId;

    @NotNull(message = "La quantità è obbligatoria")
    @Min(value = 1, message = "La quantità deve essere almeno 1")
    private Integer quantity;

    private Integer peopleNumber;
}
