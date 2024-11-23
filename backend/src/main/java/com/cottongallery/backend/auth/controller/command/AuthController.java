package com.cottongallery.backend.auth.controller.command;

import com.cottongallery.backend.auth.controller.command.api.AuthApi;
import com.cottongallery.backend.auth.service.query.AccountQueryService;
import com.cottongallery.backend.auth.utils.CookieUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cottongallery.backend.auth.dto.auth.AuthRequest;
import com.cottongallery.backend.auth.dto.auth.LoginResponse;
import com.cottongallery.backend.auth.service.AuthService;
import com.cottongallery.backend.auth.utils.TokenProvider;
import com.cottongallery.backend.common.dto.Response;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController implements AuthApi {
    private final AccountQueryService accountQueryService;

    private final AuthService authService;
    private final TokenProvider tokenProvider;
    private final CookieUtils cookieUtils;

    @Value("${jwt.authorization-header-access}")
    private String AUTHORIZATION_HEADER_ACCESS;
    
    @Value("${jwt.authorization-header-refresh}")
    private String AUTHORIZATION_HEADER_REFRESH;

    @Override
    @PostMapping("/login")
    public ResponseEntity<Response<?>> login(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
        log.info("Login request for user: {}", authRequest.getUsername());
        Map<String, String> tokens = authService.login(authRequest);

        // Access Token 설정
        Cookie accessTokenCookie = cookieUtils.createCookie(
            AUTHORIZATION_HEADER_ACCESS,
            tokens.get("accessToken"),
            tokenProvider.getExpiration(tokens.get("accessToken"))
        );
        response.addCookie(accessTokenCookie);
        // Refresh Token 설정
        Cookie refreshTokenCookie = cookieUtils.createCookie(
            AUTHORIZATION_HEADER_REFRESH,
            tokens.get("refreshToken"),
            tokenProvider.getRefreshTokenExpiration(tokens.get("refreshToken"))
        );
        response.addCookie(refreshTokenCookie);
        LoginResponse loginResponse = new LoginResponse(accountQueryService.getNameByUsername(authRequest.getUsername()));

        return new ResponseEntity<>(
            Response.createResponse(HttpStatus.OK.value(), "로그인이 성공적으로 완료되었습니다.", loginResponse),
            HttpStatus.OK
        );
    }

    @Override
    @PostMapping("/logout")
    public ResponseEntity<Response> logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        String refreshToken = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (AUTHORIZATION_HEADER_ACCESS.equals(cookie.getName())) {
                    cookieUtils.deleteCookie(response, AUTHORIZATION_HEADER_ACCESS);
                }
                if (AUTHORIZATION_HEADER_REFRESH.equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    cookieUtils.deleteCookie(response, AUTHORIZATION_HEADER_REFRESH);
                }
            }
        }

        if (refreshToken != null) {
            // Refresh Token 무효화
            String username = tokenProvider.getUsernameFromToken(refreshToken);
            authService.logout(username, refreshToken);
        }

        return new ResponseEntity<>(Response.createResponseWithoutData(HttpStatus.NO_CONTENT.value(), "로그아웃이 성공적으로 완료되었습니다."), HttpStatus.NO_CONTENT);
    }

    @Override
    @PostMapping("/refresh")
    public ResponseEntity<Response<?>> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        // Refresh Token 가져오기
        Cookie[] cookies = request.getCookies();
        String refreshToken = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (AUTHORIZATION_HEADER_REFRESH.equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                }
            }
        }

        if (refreshToken != null && tokenProvider.validateRefreshToken(refreshToken)) {
            String newAccessToken = tokenProvider.createTokenFromRefreshToken(refreshToken);

            // 새로운 Access Token 설정
            Cookie accessTokenCookie = cookieUtils.createCookie(
                AUTHORIZATION_HEADER_ACCESS,
                newAccessToken,
                tokenProvider.getExpiration(newAccessToken)
            );
            response.addCookie(accessTokenCookie);

            return new ResponseEntity<>(Response.createResponseWithoutData(HttpStatus.OK.value(), "토큰이 갱신되었습니다."), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Response.createResponseWithoutData(HttpStatus.UNAUTHORIZED.value(), "유효하지 않은 Refresh Token입니다."), HttpStatus.UNAUTHORIZED);
        }
    }
}
