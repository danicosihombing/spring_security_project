package com.cocovrend.springsecurityproject.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionRespone> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errors = new StringBuilder();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.append(fieldError.getField());
            errors.append(" ");
            errors.append(fieldError.getDefaultMessage());
            errors.append(" and ");
        }
        if (errors.length() > 5) {
            errors.delete(errors.length() - 5, errors.length());
        }
        log.error(errors.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionRespone.builder()
                        .message(errors.toString())
                        .httpStatus(HttpStatus.BAD_REQUEST.value())
                        .time(ZonedDateTime.now(ZoneId.of("UTC")))
                        .build());
    }



}
