package org.aldogioia.templatesecurity.data.dto.responses;

import lombok.Data;

@Data
public class SignInResponseDto {
    private String accessToken;
    private String refreshToken;
}
