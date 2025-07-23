package org.aldogioia.templatesecurity.data.dto.abstracts;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public abstract class ExhibitionPriceAbstractDto {
    @NotNull(message = "Il prezzo è obbligatorio")
    @PositiveOrZero(message = "Il prezzo deve essere maggiore o uguale a zero")
    private Double price;

    @NotBlank(message = "L'ID dell'esibizione è obbligatorio")
    private String exhibitionId;

    @NotBlank(message = "L'ID del tipo di biglietto è obbligatorio")
    private String ticketTypeId;
}
