package org.aldogioia.templatesecurity.data.dto.responses;

import lombok.Data;

@Data
public class ExhibitionPriceWithTicketTypeDto {
    private TicketTypeDto ticketType;
    private ExhibitionPriceDto exhibitionPrice;
}
