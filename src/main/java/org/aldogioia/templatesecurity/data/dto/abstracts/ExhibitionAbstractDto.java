package org.aldogioia.templatesecurity.data.dto.abstracts;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public abstract class ExhibitionAbstractDto {
    @NotBlank(message = "Il titolo è obbligatorio")
    @Size(min = 1, max = 100, message = "Il titolo deve essere compreso tra 1 e 100 caratteri")
    private String title;

    @NotBlank(message = "La descrizione è obbligatoria")
    @Size(min = 1, message = "La descrizione deve essere di almeno 1 carattere")
    private String description;

    @NotNull(message = "La data di inizio è obbligatoria")
    @FutureOrPresent(message = "La data di inizio deve essere oggi o una data futura")
    private LocalDate startDate;

    @NotNull(message = "La data di fine è obbligatoria")
    @Future(message = "La data di fine deve essere una data futura")
    private LocalDate endDate;

    @NotNull(message = "L'orario di inizio è obbligatorio")
    private LocalTime startTime;

    @NotNull(message = "L'orario di fine è obbligatorio")
    private LocalTime endTime;

    @NotNull(message = "La durata è obbligatoria")
    @Positive(message = "La durata deve essere maggiore di zero")
    private Integer duration;
}
