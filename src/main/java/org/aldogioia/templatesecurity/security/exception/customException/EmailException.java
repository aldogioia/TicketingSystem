package org.aldogioia.templatesecurity.security.exception.customException;

public class EmailException extends RuntimeException {
    private final String message;

    public EmailException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
