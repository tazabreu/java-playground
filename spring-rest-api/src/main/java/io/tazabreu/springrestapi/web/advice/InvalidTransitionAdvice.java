package io.tazabreu.springrestapi.web.advice;

import io.tazabreu.springrestapi.domain.exception.InvalidTransitionException;
import io.tazabreu.springrestapi.domain.exception.RestResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class InvalidTransitionAdvice {
    @ResponseBody
    @ExceptionHandler(InvalidTransitionException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    String restResourceNotFoundHandler(RestResourceNotFoundException ex) {
        return ex.getMessage();
    }
}