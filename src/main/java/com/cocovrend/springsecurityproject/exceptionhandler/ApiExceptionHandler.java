package com.cocovrend.springsecurityproject.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler{
    @ExceptionHandler(value = ExceptionImpl.class)
    public ResponseEntity<Object> handleRequestException(ExceptionImpl e){
        ExceptionRespone exceptionRespone = new ExceptionRespone(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                ZonedDateTime.now(ZoneId.of("UTC"))
        );

        log.error(e.getMessage());
        return new ResponseEntity<>(exceptionRespone, HttpStatus.BAD_REQUEST);
    }
}
