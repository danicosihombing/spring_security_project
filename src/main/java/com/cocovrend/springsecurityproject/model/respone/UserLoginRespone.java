package com.cocovrend.springsecurityproject.model.respone;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginRespone {

    private String username;
    private String token;
}
