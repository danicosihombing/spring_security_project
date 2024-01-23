package com.cocovrend.springsecurityproject.controller;

import com.cocovrend.springsecurityproject.model.request.UserLoginRequest;
import com.cocovrend.springsecurityproject.model.request.UserRegisterRequest;
import com.cocovrend.springsecurityproject.service.contract.UserAuthService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/auth")
public class UserAuthController {

    private final UserAuthService userAuthService;

    @PostMapping(path = "/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegisterRequest request){
        return userAuthService.userRegister(request);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody UserLoginRequest request){
        return userAuthService.userLogin(request);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping(path = "/logout")
    public ResponseEntity<?> logoutUser(HttpServletRequest request){
        return userAuthService.userLogout(request);
    }




}
