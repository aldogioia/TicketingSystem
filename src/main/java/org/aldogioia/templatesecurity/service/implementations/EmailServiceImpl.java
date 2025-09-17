package org.aldogioia.templatesecurity.service.implementations;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.aldogioia.templatesecurity.security.exception.customException.EmailException;
import org.aldogioia.templatesecurity.service.interfaces.EmailService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;

    @Override
    public void sendRecoveryPasswordCode(String email, String code) {
        String textContent = """
            Abbiamo ricevuto una richiesta per reimpostare la tua password.
        
            Questo è il codice di verifica: %s.
        
            Scadrà entro 10 minuti.
        
            Se non hai richiesto questa operazione, ignora questa email.
        
            Grazie
            """.formatted(code);

        try {
            sendEmail(textContent, email, "Reset Password");
        } catch (MessagingException e) {
            throw new EmailException("Errore durante l'invio dell'email di recupero password");
        }
    }

    @Override
    public void sendVerificationEmail(String email, String code) {
        String textContent = "Per verificare il tuo indirizzo email, utilizza il seguente codice: " + code + ".\n\n" +
                "Se non hai richiesto questa operazione, ignora questa email.\n\n" +
                "Grazie";

        try {
            sendEmail(textContent, email, "Verifica Email");
        } catch (MessagingException e) {
            throw new EmailException("Errore durante l'invio dell'email di verifica");
        }
    }

    private void sendEmail(String content, String email, String subject) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, false);

        helper.setFrom("biglietteria@museomultimedialecosenza.it");
        helper.setTo(email);
        helper.setSubject(subject);
        helper.setText(content, false);
        javaMailSender.send(message);
    }

    private void sendEmailWithAttachment(String content, String email, byte[] pdfBytes) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("biglietteria@museomultimedialecosenza.it");
        helper.setTo(email);
        helper.setSubject("Nuovo Biglietto Acquistato");
        helper.setText(content, false);

        helper.addAttachment("biglietto.pdf", new ByteArrayResource(pdfBytes));

        javaMailSender.send(message);
    }
}
