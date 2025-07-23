package org.aldogioia.templatesecurity.security.exception.customException;

public class TicketExpiredException extends RuntimeException {
    private final String message;
    public TicketExpiredException(String message) {
        super(message);
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
