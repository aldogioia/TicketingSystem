package org.aldogioia.templatesecurity.data.dto.responses;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.aldogioia.templatesecurity.data.dto.abstracts.ExhibitionPriceAbstractDto;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExhibitionPriceDto extends ExhibitionPriceAbstractDto {
    private String id;
}
