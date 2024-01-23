package com.cocovrend.springsecurityproject.service.implementasi;

import com.cocovrend.springsecurityproject.entity.UserCredential;
import com.cocovrend.springsecurityproject.exceptionhandler.ExceptionImpl;
import com.cocovrend.springsecurityproject.model.request.UserLoginRequest;
import com.cocovrend.springsecurityproject.model.request.UserRegisterRequest;
import com.cocovrend.springsecurityproject.model.respone.CommonRespone;
import com.cocovrend.springsecurityproject.model.respone.UserLoginRespone;
import com.cocovrend.springsecurityproject.model.respone.UserRegisterRespone;
import com.cocovrend.springsecurityproject.repository.UserCredentialRepository;
import com.cocovrend.springsecurityproject.securityconfig.AuthJwtTokenFilter;
import com.cocovrend.springsecurityproject.securityconfig.CustomUserDetailsImpl;
import com.cocovrend.springsecurityproject.securityconfig.JwtUtils;
import com.cocovrend.springsecurityproject.service.contract.UserAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAuthServiceImpl implements UserAuthService {

    private final UserCredentialRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final AuthJwtTokenFilter authJwtTokenFilter;

    @Override
    public ResponseEntity<CommonRespone> userRegister(UserRegisterRequest request) {
        try {
            UserCredential userCredential = UserCredential.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .dateTime(ZonedDateTime.now(ZoneId.of("UTC")))
                    .build();
            repository.saveAndFlush(userCredential);
            log.info("Success create new user, username: {}", userCredential.getUsername());

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(CommonRespone.builder()
                            .statusCode(HttpStatus.CREATED.value())
                            .message("Succesfully register new user")
                            .data(UserRegisterRespone.builder()
                                    .username(userCredential.getUsername())
                                    .dateTime(ZonedDateTime.now(ZoneId.of("UTC")))
                                    .build())
                            .build());
        }catch (RuntimeException e){
            log.error(e.getMessage());
            throw new ExceptionImpl(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> userLogin(UserLoginRequest request){
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            ));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            CustomUserDetailsImpl userDetails = (CustomUserDetailsImpl) authentication.getPrincipal();

            String token = jwtUtils.generateToken(userDetails.getUsername());
            log.info("Login success, username: {}", userDetails.getUsername());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(CommonRespone.builder()
                            .statusCode(HttpStatus.OK.value())
                            .message("Login success")
                            .data(UserLoginRespone.builder()
                                    .username(userDetails.getUsername())
                                    .token(token)
                                    .build()).build());
        }catch (RuntimeException e){
            log.error(e.getMessage());
            throw new ExceptionImpl(e.getMessage());

        }

    }

    @Override
    public ResponseEntity<?> userLogout(HttpServletRequest request) {
        String jwt = authJwtTokenFilter.parseJwt(request);
        if (jwt!=null && !jwtUtils.isTokenBlacklisted(jwt)){
            SecurityContextHolder.clearContext();
            jwtUtils.addToBlacklist(jwt);
            log.info("Success logout");
            return ResponseEntity.status(HttpStatus.OK)
                    .body(CommonRespone.builder()
                            .statusCode(HttpStatus.OK.value())
                            .message("Succes logout")
                            .data(null)
                            .build());

        }else {
            throw new ExceptionImpl("You are not login");
        }



    }
}
