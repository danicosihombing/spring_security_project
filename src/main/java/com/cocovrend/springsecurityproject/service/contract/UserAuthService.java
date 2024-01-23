package com.cocovrend.springsecurityproject.service.contract;

import com.cocovrend.springsecurityproject.model.request.UserLoginRequest;
import com.cocovrend.springsecurityproject.model.request.UserRegisterRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface UserAuthService {
    ResponseEntity<?> userRegister(UserRegisterRequest request);
    ResponseEntity<?> userLogin(UserLoginRequest request);
    ResponseEntity<?> userLogout(HttpServletRequest request);

}
