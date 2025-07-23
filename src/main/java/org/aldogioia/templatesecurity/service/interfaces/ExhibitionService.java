package org.aldogioia.templatesecurity.service.interfaces;

import org.aldogioia.templatesecurity.data.dto.creates.ExhibitionCreateDto;
import org.aldogioia.templatesecurity.data.dto.responses.ExhibitionDto;
import org.aldogioia.templatesecurity.data.dto.updates.ExhibitionUpdateDto;

import java.util.List;

public interface ExhibitionService {
    /**
     * Metodo per recuperare tutte le esposizioni.
     *
     * @return Una lista di esposizioni.
     */
    List<ExhibitionDto> getExhibitions();

    /**
     * Metodo per creare una nuova esposizione.
     *
     * @param exhibitionCreateDto Dettagli dell'esposizione da creare.
     * @return L'esposizione creata.
     */
    ExhibitionDto createExhibition(ExhibitionCreateDto exhibitionCreateDto);

    /**
     * Metodo per aggiornare un'esposizione esistente.
     *
     * @param exhibitionUpdateDto Dettagli aggiornati dell'esposizione.
     * @return L'esposizione aggiornata.
     */
    ExhibitionDto updateExhibition(ExhibitionUpdateDto exhibitionUpdateDto);

    /**
     * Metodo per eliminare un'esposizione tramite il suo ID.
     *
     * @param exhibitionId ID dell'esposizione da eliminare.
     */
    void deleteExhibition(String exhibitionId);
}
