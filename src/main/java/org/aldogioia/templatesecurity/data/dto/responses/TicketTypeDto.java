package org.aldogioia.templatesecurity.data.dto.responses;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.aldogioia.templatesecurity.data.dto.abstracts.TicketTypeAbstractDto;

@Data
@EqualsAndHashCode(callSuper = true)
public class TicketTypeDto extends TicketTypeAbstractDto {
    private String id;
}
