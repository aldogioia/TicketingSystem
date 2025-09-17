package org.aldogioia.templatesecurity.service.interfaces;

public interface EmailService {
    void sendRecoveryPasswordCode(String email, String code);
    void sendVerificationEmail(String email, String code);
}
