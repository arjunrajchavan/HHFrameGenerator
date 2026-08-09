package com.BuilderBadge.HHframeGenerator.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(com.BuilderBadge.HHframeGenerator.exceptions.BadgeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(com.BuilderBadge.HHframeGenerator.exceptions.BadgeNotFoundException ex) {
        return ex.getMessage();
    }
}