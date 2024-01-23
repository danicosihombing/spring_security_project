package com.cocovrend.springsecurityproject.exceptionhandler;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
public class ExceptionRespone<T> {
    private final String message;
    private final T httpStatus;
    private final ZonedDateTime time;
}
