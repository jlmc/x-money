package com.xcosta.xmoney.api;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@ControllerAdvice
public class XMoneyExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    private final Function<Exception, String> devMessageMapper = (ex) -> ex.getCause() == null ? ex.getMessage() : ex.getCause().getMessage();

    @Autowired
    public XMoneyExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        String userMessage = messageSource.getMessage("message.invalid", null, LocaleContextHolder.getLocale());
        String devMessage = this.devMessageMapper.apply(ex);
        return handleExceptionInternal(ex, new Error(userMessage, devMessage), headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ EmptyResultDataAccessException.class })
    //@ResponseStatus(HttpStatus.NOT_FOUND) // without message
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
        String userMessage = messageSource.getMessage("resource.not-found", null, LocaleContextHolder.getLocale());
        String devMessage = this.devMessageMapper.apply(ex);
        return handleExceptionInternal(ex, Collections.singletonList(new Error(userMessage, devMessage)),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({ DataIntegrityViolationException.class, ConstraintViolationException.class })
    //@ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> handleDataIntegrityViolationException(Exception ex,
                                                                         WebRequest request) {
        String userMessage = messageSource.getMessage("resource.conflict", null, LocaleContextHolder.getLocale());
        String devMessage = this.devMessageMapper.apply(ex);
        return handleExceptionInternal(ex, Collections.singletonList(new Error(userMessage, devMessage)),
                new HttpHeaders(), HttpStatus.CONFLICT, request);

    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        List<Error> errors = createErrors(ex.getBindingResult());
        return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
    }

    private List<Error> createErrors(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
                .map(fieldError -> new Error(
                            messageSource.getMessage(fieldError, LocaleContextHolder.getLocale()),
                            fieldError.toString())
                ).collect(Collectors.toList());
    }



    public static class Error {

        private final String userMessage;
        private final String devMessage;

        public Error(String userMessage, String devMessage) {
            this.userMessage = userMessage;
            this.devMessage = devMessage;
        }

        public String getUserMessage() {
            return userMessage;
        }

        public String getDevMessage() {
            return devMessage;
        }

    }

}
