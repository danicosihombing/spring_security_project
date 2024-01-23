package com.cocovrend.springsecurityproject.model.respone;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
public class UserRegisterRespone {

    private String username;
    private ZonedDateTime dateTime;

}

