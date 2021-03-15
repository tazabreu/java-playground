package io.tazabreu.springrestapi.web.advice;

import io.tazabreu.springrestapi.domain.exception.RestResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class RestResourceNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(RestResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String restResourceNotFoundHandler(RestResourceNotFoundException ex) {
        return ex.getMessage();
    }
}