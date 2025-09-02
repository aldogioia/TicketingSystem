package org.aldogioia.templatesecurity.service.implementations;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.aldogioia.templatesecurity.data.entities.Ticket;
import org.aldogioia.templatesecurity.security.exception.customException.EmailException;
import org.aldogioia.templatesecurity.service.interfaces.EmailService;
import org.aldogioia.templatesecurity.utils.PdfGenerator;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    private final PdfGenerator pdfGenerator;

    @Override
    public void sendRecoveryPasswordCode(String email, String code) {
        String textContent = "Abbiamo ricevuto una richiesta per reimpostare la tua password.\n" +
                "Questo e il codice di verifica: "+ code + "\n\n" +
                "Se non hai richiesto questa operazione, ignora questa email.\n\n" +
                "Grazie";

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

    @Override
    public void sendConfirmNewsletterSubscribe(String email) {
        String textContent = "Grazie per esserti iscritto alla nostra newsletter!\n" +
                "Riceverai aggiornamenti sulle nostre mostre e eventi.\n\n" +
                "Se non hai richiesto questa operazione, ignora questa email.\n\n" +
                "Grazie";

        try {
            sendEmail(textContent, email, "Conferma Iscrizione Newsletter");
        } catch (MessagingException e) {
            throw new EmailException("Errore durante l'invio dell'email di conferma iscrizione newsletter");
        }
    }

    @Override
    public void sendNewsletterEmail() {
    }

    @Override
    public void sendTicketEmail(String email, Ticket ticket) {
        String textContent = "Ciao, " + ticket.getUser().getName() +".\n\n" +
                "Hai acquistato un biglietto per la mostra: " +
                ticket.getExhibitionPrice().getExhibition().getTitle() + ".\n"+
                "In allegato trovi il tuo biglietto in formato PDF.\n\nGrazie e a presto!\n\n\n\n";

        try {
            byte[] pdfBytes = pdfGenerator.generatePdfTicket(ticket);

            sendEmailWithAttachment(textContent, email, pdfBytes);

        } catch (Exception e) {
            throw new EmailException("Errore durante l'invio dell'email del biglietto");
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

//    public void sendNewsletterEmail(
//            String toEmail,
//            String subject,
//            String messageText,
//            byte[] attachmentBytes,
//            String attachmentFilename,
//            String unsubscribeToken
//    ) {
//        try {
//            MimeMessage message = javaMailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
//
//            helper.setFrom("newsletter@miosito.it");
//            helper.setTo(toEmail);
//            helper.setSubject(subject);
//
//            if (unsubscribeToken != null) {
//
//            }
//            String unsubscribeLink = unsubscribeBaseUrl + "?email=" + toEmail + "&token=" + unsubscribeToken;
//
//            String htmlContent =
//                    "<html><body>" +
//                            "<p style='font-size:16px;color:#303030;'>" + messageText + "</p>" +
//                            "<p style='margin-top:20px;font-size:12px;color:#636363;'>Se non desideri più ricevere questa newsletter, " +
//                            "<a href='" + unsubscribeLink + "'>clicca qui per disiscriverti</a>.</p>" +
//                            "</body></html>";
//
//            helper.setText(htmlContent, true);
//
//            if (attachmentBytes != null && attachmentBytes.length > 0) {
//                helper.addAttachment(attachmentFilename, new ByteArrayResource(attachmentBytes));
//            }
//
//            javaMailSender.send(message);
//
//        } catch (Exception e) {
//            throw new RuntimeException("Errore nell'invio della newsletter", e);
//        }
//    }
}
