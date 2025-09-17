package org.aldogioia.templatesecurity.security.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.aldogioia.templatesecurity.data.dto.responses.ErrorDto;
import org.aldogioia.templatesecurity.security.exception.customException.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Gestore globale delle eccezioni per l'applicazione.
 * <p>
 * Questa classe intercetta tutte le eccezioni non gestite dai controller
 * e restituisce una risposta strutturata con dettagli sull'errore.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> onValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return errors;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto onException(WebRequest req, Exception e) {
        return createErrorResponse(req, e.getMessage());
    }

    @ExceptionHandler(TokenException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto onTokenException(WebRequest req, TokenException e) {
        return createErrorResponse(req, e.getMessage());
    }

    @ExceptionHandler(TokenExpiredException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDto onTokenExpiredException(WebRequest req, TokenExpiredException e) {
        return createErrorResponse(req, e.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto onEntityNotFoundException(WebRequest req, EntityNotFoundException e) {
        return createErrorResponse(req, e.getMessage());
    }

    @ExceptionHandler(TicketExpiredException.class)
    @ResponseStatus(HttpStatus.GONE)
    public ErrorDto onTicketExpiredException(WebRequest req, TicketExpiredException e) {
        return createErrorResponse(req, e.getMessage());
    }

    @ExceptionHandler(TicketInvalidException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorDto onTicketInvalidException(WebRequest req, TicketInvalidException e) {
        return createErrorResponse(req, e.getMessage());
    }

    @ExceptionHandler(EmailException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto onEmailException(WebRequest req, EmailException e) {
        return createErrorResponse(req, e.getMessage());
    }

    private ErrorDto createErrorResponse(WebRequest req, String message) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) req.resolveReference("request");
        assert httpServletRequest != null;
        return new ErrorDto(new Date(), httpServletRequest.getRequestURI(), message);
    }
}
