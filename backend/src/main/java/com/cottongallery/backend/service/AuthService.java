package com.cottongallery.backend.service;

import com.cottongallery.backend.dto.auth.AuthRequest;
import com.cottongallery.backend.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class); // 올바르게 설정됨

    public String login(AuthRequest authRequest) {
        logger.info("Login request for user in Auth Service: {} {}", authRequest.getUsername(), authRequest.getPassword());
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(),
                    authRequest.getPassword()
                )
            );
            logger.info("Authentication successful for user: {}", authRequest.getUsername());

            // 토큰 생성
            String token = tokenProvider.createToken(authentication);
            logger.info("Generated Token: {}", token);

            return token;
        } catch (AuthenticationException e) {
            logger.error("Authentication failed for user: {}", authRequest.getUsername(), e);
            throw e; // 예외를 다시 던져서 컨트롤러에서 처리하게 함
        }
    }
}
