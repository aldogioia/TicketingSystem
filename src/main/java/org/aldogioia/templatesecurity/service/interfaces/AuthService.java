package org.aldogioia.templatesecurity.service.interfaces;

import jakarta.servlet.http.HttpServletRequest;
import org.aldogioia.templatesecurity.data.dto.responses.AuthResponseDto;
import org.aldogioia.templatesecurity.data.dto.creates.CustomerCreateDto;

public interface AuthService {
    AuthResponseDto signIn(String email, String password);
    void signUp(CustomerCreateDto customerCreateDto);
    String refresh(HttpServletRequest request);
    void signOut(HttpServletRequest request);
}
