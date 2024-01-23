package com.cocovrend.springsecurityproject.model.respone;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommonRespone<T> {

    private Integer statusCode;
    private String message;
    private T data;
}
