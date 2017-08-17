package com.xcosta.xmoney.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class XMoneyExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

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
        String devMessage = ex.getCause().toString();
        return handleExceptionInternal(ex, new Error(userMessage, devMessage), headers, HttpStatus.BAD_REQUEST, request);
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
