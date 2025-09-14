package org.aldogioia.templatesecurity.data.dto.responses;

import lombok.Data;

import java.util.List;

@Data
public class TicketsPdfDto {
    private List<TicketDto> tickets;
    private byte[] pdf;
}
