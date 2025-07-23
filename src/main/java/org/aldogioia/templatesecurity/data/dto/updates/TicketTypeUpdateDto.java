package org.aldogioia.templatesecurity.data.dto.updates;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.aldogioia.templatesecurity.data.dto.abstracts.TicketTypeAbstractDto;

@Data
@EqualsAndHashCode(callSuper = true)
public class TicketTypeUpdateDto extends TicketTypeAbstractDto {
    @NotBlank(message = "L'Id è obbligatorio")
    private String id;
}
