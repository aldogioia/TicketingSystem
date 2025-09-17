package org.aldogioia.templatesecurity.security.exception.customException;

public class TicketInvalidException extends RuntimeException {
    private final String message;
    public TicketInvalidException(String message) {
        super(message);
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
