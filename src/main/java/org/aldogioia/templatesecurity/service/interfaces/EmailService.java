package org.aldogioia.templatesecurity.service.interfaces;

import jakarta.mail.MessagingException;
import org.aldogioia.templatesecurity.data.entities.Ticket;

public interface EmailService {
    void sendRecoveryPasswordCode(String email, String code) throws MessagingException;
    void sendVerificationEmail(String email, String code);
    void sendConfirmNewsletterSubscribe(String email);
    void sendNewsletterEmail();
    void sendTicketEmail(String email, Ticket ticket);
}
