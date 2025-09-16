package org.aldogioia.templatesecurity.service.interfaces;

import jakarta.servlet.http.HttpServletRequest;
import org.aldogioia.templatesecurity.data.dto.responses.RefreshResponseDto;
import org.aldogioia.templatesecurity.data.dto.responses.SignInResponseDto;
import org.aldogioia.templatesecurity.data.dto.creates.CustomerCreateDto;

public interface AuthService {
    SignInResponseDto signIn(String email, String password);
    void signUp(CustomerCreateDto customerCreateDto);
    void updateRequest(String email);
    void updatePassword(String email, String password,String code);
    RefreshResponseDto refresh(HttpServletRequest request);
    void signOut(HttpServletRequest request);
}
