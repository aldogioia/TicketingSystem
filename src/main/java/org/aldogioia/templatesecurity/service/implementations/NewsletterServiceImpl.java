package org.aldogioia.templatesecurity.service.implementations;

import lombok.RequiredArgsConstructor;
import org.aldogioia.templatesecurity.data.dao.NewsletterDao;
import org.aldogioia.templatesecurity.data.entities.Newsletter;
import org.aldogioia.templatesecurity.service.interfaces.EmailService;
import org.aldogioia.templatesecurity.service.interfaces.NewsletterService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsletterServiceImpl implements NewsletterService {
    private final NewsletterDao newsletterDao;
    private final EmailService emailService;

    public void subscribeToNewsletter(String email) {
        if (newsletterDao.existsByEmail(email)) {
            throw new IllegalArgumentException("Questa email è già iscritta alla newsletter");
        }
        Newsletter newsletter = new Newsletter();
        newsletter.setEmail(email);

        emailService.sendConfirmNewsletterSubscribe(newsletter.getEmail());

        newsletterDao.save(newsletter);
    }

    public void unsubscribeFromNewsletter(String email) {
        Newsletter newsletter = newsletterDao.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Questa email non è iscritta alla newsletter"));

        newsletterDao.delete(newsletter);
    }
}