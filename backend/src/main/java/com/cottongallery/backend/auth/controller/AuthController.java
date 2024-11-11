package com.cottongallery.backend.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cottongallery.backend.auth.dto.auth.AuthRequest;
import com.cottongallery.backend.auth.dto.auth.LoginResponse;
import com.cottongallery.backend.auth.service.AuthService;
import com.cottongallery.backend.auth.utils.TokenProvider;
import com.cottongallery.backend.common.dto.DataResponse;
import com.cottongallery.backend.common.dto.Response;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final TokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
        log.info("Login request for user: {}", authRequest.getUsername());
        Map<String, String> tokens = authService.login(authRequest);

        // Access Token 설정
        Cookie accessTokenCookie = new Cookie("AccessToken", tokens.get("accessToken"));
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge((int) (tokenProvider.getExpiration(tokens.get("accessToken")) / 1000));
        response.addCookie(accessTokenCookie);

        // Refresh Token 설정
        Cookie refreshTokenCookie = new Cookie("RefreshToken", tokens.get("refreshToken"));
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge((int) (tokenProvider.getRefreshTokenExpiration(tokens.get("refreshToken")) / 1000));
        response.addCookie(refreshTokenCookie);

        LoginResponse loginResponse = new LoginResponse(authRequest.getUsername());

        return new ResponseEntity<>(new DataResponse(HttpStatus.OK.value(), "로그인이 성공적으로 완료되었습니다.", loginResponse),
                HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Response> logout(HttpServletRequest request, HttpServletResponse response) {
        // 쿠키에서 토큰 삭제
        Cookie[] cookies = request.getCookies();
        String refreshToken = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("AccessToken".equals(cookie.getName())) {
                    Cookie deleteCookie = new Cookie("AccessToken", null);
                    deleteCookie.setPath("/");
                    deleteCookie.setHttpOnly(true);
                    deleteCookie.setMaxAge(0);
                    response.addCookie(deleteCookie);
                }
                if ("RefreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    Cookie deleteCookie = new Cookie("RefreshToken", null);
                    deleteCookie.setPath("/");
                    deleteCookie.setHttpOnly(true);
                    deleteCookie.setMaxAge(0);
                    response.addCookie(deleteCookie);
                }
            }
        }

        if (refreshToken != null) {
            // Refresh Token 무효화
            String username = tokenProvider.getUsernameFromToken(refreshToken);
            authService.logout(username);
        }

        return new ResponseEntity<>(new Response(HttpStatus.NO_CONTENT.value(), "로그아웃이 성공적으로 완료되었습니다."),
                HttpStatus.NO_CONTENT);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Response> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        // Refresh Token 가져오기
        Cookie[] cookies = request.getCookies();
        String refreshToken = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("RefreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                }
            }
        }

        if (refreshToken != null && tokenProvider.validateRefreshToken(refreshToken)) {
            String newAccessToken = tokenProvider.createTokenFromRefreshToken(refreshToken);

            // 새로운 Access Token 설정
            Cookie accessTokenCookie = new Cookie("AccessToken", newAccessToken);
            accessTokenCookie.setHttpOnly(true);
            accessTokenCookie.setPath("/");
            accessTokenCookie.setMaxAge((int) (tokenProvider.getExpiration(newAccessToken) / 1000));
            response.addCookie(accessTokenCookie);

            return new ResponseEntity<>(new Response(HttpStatus.OK.value(), "토큰이 갱신되었습니다."), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Response(HttpStatus.UNAUTHORIZED.value(), "유효하지 않은 Refresh Token입니다."),
                    HttpStatus.UNAUTHORIZED);
        }
    }
}
