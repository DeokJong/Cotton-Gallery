package com.cottongallery.backend.auth.service;

import com.cottongallery.backend.auth.dto.auth.AuthRequest;
import com.cottongallery.backend.auth.utils.TokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;

    public Map<String, String> login(AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(),
                    authRequest.getPassword()
                )
            );
            log.info("Authentication successful for user: {}", authRequest.getUsername());

            // 토큰 생성
            String accessToken = tokenProvider.createToken(authentication);
            String refreshToken = tokenProvider.createRefreshToken(authentication);

            // Refresh Token 저장
            refreshTokenService.saveRefreshToken(authRequest.getUsername(), refreshToken);

            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", accessToken);
            tokens.put("refreshToken", refreshToken);

            return tokens;
        } catch (AuthenticationException e) {
            log.error("Authentication failed for user: {}", authRequest.getUsername(), e);
            throw e;
        }
    }

    public void logout(String username) {
        // Refresh Token 삭제
        refreshTokenService.deleteRefreshToken(username);
        log.info("Logged out user: {}", username);
    }
}
