package org.aldogioia.templatesecurity.data.dto.responses;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.aldogioia.templatesecurity.data.dto.abstracts.ExhibitionAbstractDto;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExhibitionDto extends ExhibitionAbstractDto {
    private String id;
}
