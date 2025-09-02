package org.aldogioia.templatesecurity.service.interfaces;

public interface NewsletterService {
    void subscribeToNewsletter(String email);
    void unsubscribeFromNewsletter(String email);
}
