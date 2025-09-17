package org.aldogioia.templatesecurity.utils;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.aldogioia.templatesecurity.data.entities.Exhibition;
import org.aldogioia.templatesecurity.data.entities.Ticket;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class PdfGenerator {
    private final TemplateEngine templateEngine;

    public PdfGenerator(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    private final ExecutorService qrExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public byte[] generatePdfTickets(List<Ticket> tickets) throws Exception {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();

            String logoBase64 = Base64Converter.imageUrlToBase64("https://museo-multimediale-cosenza.s3.eu-north-1.amazonaws.com/logo.png");
            String bannerBase64 = Base64Converter.imageUrlToBase64("https://museo-multimediale-cosenza.s3.eu-north-1.amazonaws.com/AD-mostra.jpeg");

            List<CompletableFuture<String>> qrFutures = tickets.stream()
                    .map(ticket -> CompletableFuture.supplyAsync(() -> {
                        try {
                            return Base64Converter.generateQrCodeBase64(ticket.getId());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }, qrExecutor))
                    .toList();

            List<String> qrCodes = qrFutures.stream()
                    .map(CompletableFuture::join)
                    .toList();

            StringBuilder allHtml = new StringBuilder(
                    "<html><head><meta charset='UTF-8'/><style>" +
                            " @page { size: A4; margin: 1cm; } " +
                            " * { box-sizing: border-box; margin: 0; padding: 0; } " +
                            " body { font-family: Arial, Helvetica, sans-serif; color: #303030; } " +
                            " h1 { font-size: 22px; margin-bottom: 20px; } " +
                            " h4 { font-size: 12px; font-weight: 500; color: #636363; margin-bottom: 5px; } " +
                            " p { font-size: 16px; } " +
                            " span { font-size: 8px; color: #636363; } " +
                            " header { width: 100%; margin-bottom: 10px; overflow: hidden; } " +
                            " header img:first-child { float: left; height: 80px; } " +
                            " header img:last-child { float: right; height: 80px; } " +
                            " .divider { width: 100%; height: 1px; background-color: #d9d9d9; margin: 5px 0; } " +
                            " .row { width: 100%; overflow: hidden; } " +
                            " .column { display: inline-block; vertical-align: top; width: 48%; margin-right: 2%; } " +
                            " .column:last-child { margin-right: 0; } " +
                            " footer { margin-top: 10px; } " +
                            " footer img { width: 100%; height: auto; } " +
                            "</style></head><body>"
            );

            for (int i = 0; i < tickets.size(); i++) {
                Ticket ticket = tickets.get(i);
                String qrBase64 = qrCodes.get(i);
                allHtml.append(generateTicketHtml(ticket, qrBase64, logoBase64, bannerBase64));

                if (i < tickets.size() - 1) {
                    allHtml.append("<div style='page-break-after: always;'></div>");
                }
            }

            allHtml.append("</body></html>");

            builder.withHtmlContent(allHtml.toString(), null);
            builder.toStream(os);
            builder.run();

            return os.toByteArray();
        }
    }

    private String generateTicketHtml(Ticket ticket, String qrBase64, String logoBase64, String bannerBase64) {
        Exhibition exhibition = ticket.getExhibitionPrice().getExhibition();

        Context context = new Context();
        context.setVariable("exhibitionTitle", exhibition.getTitle());
        context.setVariable("startDate", exhibition.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        context.setVariable("endDate", exhibition.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        context.setVariable("issuedOn", ticket.getIssuedOn().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        context.setVariable("expireOn", exhibition.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        context.setVariable("ticketType", ticket.getExhibitionTitle());
        context.setVariable("price", String.format("%.2f", ticket.getPriceAtPurchase()).replace('.', ',') + " €");
        context.setVariable("times", "Dalle " + exhibition.getStartTime() + " alle " + exhibition.getEndTime());
        context.setVariable("entries", "Valido per l'ingresso di " + ticket.getPeopleNumber() + " person" + (ticket.getPeopleNumber() > 1 ? "e" : "a"));
        context.setVariable("duration", exhibition.getDuration() + " minuti");
        context.setVariable("bannerUrl", bannerBase64);
        context.setVariable("qrCodeBase64", qrBase64);
        context.setVariable("logoUrl", logoBase64);

        return templateEngine.process("ticket-template", context);
    }
}
