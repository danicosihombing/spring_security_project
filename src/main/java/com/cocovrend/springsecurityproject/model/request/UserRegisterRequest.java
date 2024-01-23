package com.cocovrend.springsecurityproject.model.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
public class UserRegisterRequest {

    @NotBlank
    private String username;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d#$@!%&*?]{8,20}$",
            message = "please must enter min 1 uppercase, min 1 lowercase, min 1 number, min 8 characters and max 20 characters")
    private String password;
}
