package org.aldogioia.templatesecurity.data.dto.responses;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.aldogioia.templatesecurity.data.dto.abstracts.TicketAbstractDto;
import org.aldogioia.templatesecurity.data.enumerators.TicketStatus;

@Data
@EqualsAndHashCode(callSuper = true)
public class TicketDto extends TicketAbstractDto {
    private String id;
    private String issuedOn;
    private String priceAtPurchase;
    private TicketStatus status;
}
