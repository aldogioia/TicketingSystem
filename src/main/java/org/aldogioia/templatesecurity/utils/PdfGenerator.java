package org.aldogioia.templatesecurity.utils;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import org.aldogioia.templatesecurity.data.entities.Exhibition;
import org.aldogioia.templatesecurity.data.entities.Ticket;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.*;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

@Component
public class PdfGenerator {
    private final TemplateEngine templateEngine;

    public PdfGenerator(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public byte[] generatePdfTicket(Ticket ticket) throws Exception {
        String qrBase64 = generateQrCodeBase64(ticket.getId());
        String bannerBase64 = imageUrlToBase64("https://museo-multimediale-cosenza.s3.eu-north-1.amazonaws.com/AD-banner.jpg");
        String logoBase64 = imageUrlToBase64("https://museo-multimediale-cosenza.s3.eu-north-1.amazonaws.com/logo.png");

        Exhibition exhibition = ticket.getExhibitionPrice().getExhibition();

        Context context = new Context();
        context.setVariable("exhibitionTitle", exhibition.getTitle());
        context.setVariable("startDate", exhibition.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        context.setVariable("endDate", exhibition.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        context.setVariable("issuedOn", ticket.getIssuedOn().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        context.setVariable("expireOn", exhibition.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        context.setVariable("ticketType", ticket.getExhibitionPrice().getTicketType().getName());
        context.setVariable("price", ticket.getPriceAtPurchase());
        context.setVariable("times", "Dalle " + exhibition.getStartTime() + " alle " + exhibition.getEndTime());
        context.setVariable("duration", exhibition.getDuration());
        context.setVariable("bannerUrl", bannerBase64);
        context.setVariable("qrCodeBase64", qrBase64);
        context.setVariable("logoUrl", logoBase64);

        String html = templateEngine.process("ticket-template", context);

        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(html, null);
            builder.toStream(os);
            builder.run();
            return os.toByteArray();
        }
    }

    public String imageUrlToBase64(String imageUrl) throws IOException {
        try (InputStream in = new URL(imageUrl).openStream()) {
            byte[] bytes = in.readAllBytes();
            return Base64.getEncoder().encodeToString(bytes);
        }
    }

    private String generateQrCodeBase64(String text) throws IOException {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 100, 100);
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", pngOutputStream);
            return Base64.getEncoder().encodeToString(pngOutputStream.toByteArray());
        } catch (WriterException e) {
            throw new IOException("Errore generazione QR Code", e);
        }
    }
}
