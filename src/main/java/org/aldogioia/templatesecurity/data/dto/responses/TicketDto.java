package org.aldogioia.templatesecurity.data.dto.responses;

import lombok.Data;
import org.aldogioia.templatesecurity.data.enumerators.TicketStatus;

@Data
public class TicketDto {
    private String id;
    private String issuedOn;
    private Double priceAtPurchase;
    private String exhibitionTitle;
    private TicketStatus status;
}
