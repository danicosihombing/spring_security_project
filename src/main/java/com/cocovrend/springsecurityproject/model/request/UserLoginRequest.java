package com.cocovrend.springsecurityproject.model.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class UserLoginRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
