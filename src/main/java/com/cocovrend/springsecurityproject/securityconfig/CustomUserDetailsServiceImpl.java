package com.cocovrend.springsecurityproject.securityconfig;

import com.cocovrend.springsecurityproject.entity.UserCredential;
import com.cocovrend.springsecurityproject.exceptionhandler.ExceptionImpl;
import com.cocovrend.springsecurityproject.repository.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private final UserCredentialRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCredential credential = repository.findByUsername(username)
                .orElseThrow(()-> new ExceptionImpl("Username not found"));

        return new CustomUserDetailsImpl(credential.getUsername(), credential.getPassword());
    }
}
