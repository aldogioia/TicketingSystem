package org.aldogioia.templatesecurity.data.dto.updates;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.aldogioia.templatesecurity.data.dto.abstracts.ExhibitionAbstractDto;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExhibitionUpdateDto extends ExhibitionAbstractDto {
    @NotBlank(message = "L'Id è obbligatorio")
    private String id;
}
