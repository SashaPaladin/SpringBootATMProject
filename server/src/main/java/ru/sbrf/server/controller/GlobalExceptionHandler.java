package ru.sbrf.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.sbrf.common.messages.ErrorMessage;
import ru.sbrf.server.exception.AuthException;
import ru.sbrf.server.exception.ProcessingException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorMessage AuthExceptionHandler(AuthException ex) {
        return new ErrorMessage(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorMessage UsernameNotFoundException(UsernameNotFoundException ex) {
        return new ErrorMessage(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorMessage ProcessExceptionException(ProcessingException ex) {
        return new ErrorMessage(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }
}
