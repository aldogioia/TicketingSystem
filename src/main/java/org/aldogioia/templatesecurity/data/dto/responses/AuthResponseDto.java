package org.aldogioia.templatesecurity.data.dto.responses;

import lombok.Data;

@Data
public class AuthResponseDto {
    private String accessToken;
    private String refreshToken;
}
