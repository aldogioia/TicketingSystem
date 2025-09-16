package org.aldogioia.templatesecurity.service.interfaces;

public interface PasswordCodeService {
    void createCode(String email);
    boolean validateCode(String email, String code);
}
