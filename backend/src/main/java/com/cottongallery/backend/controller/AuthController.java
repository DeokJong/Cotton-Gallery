package com.cottongallery.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cottongallery.backend.dto.auth.AuthRequest;
import com.cottongallery.backend.dto.auth.AuthResponse;
import com.cottongallery.backend.security.JwtAuthenticationFilter;
import com.cottongallery.backend.security.TokenProvider;
import com.cottongallery.backend.service.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {
  private final AuthService authService;
  private final TokenProvider tokenProvider;
  private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
    logger.info("Login request for user: {}", authRequest.getUsername());
    String token = authService.login(authRequest);
    // HttpOnly 쿠키에 토큰 설정
    Cookie cookie = new Cookie("Authorization", token);
    cookie.setHttpOnly(true);
    cookie.setPath("/");
    cookie.setSecure(true); // HTTPS 사용 시 true로 설정
    cookie.setMaxAge((int) (tokenProvider.getExpiration(token) / 1000)); // 토큰 만료 시간 설정 (초 단위)
    response.addCookie(cookie);

    return ResponseEntity.ok(new AuthResponse(token, token));
  }

  @PostMapping("/logout")
  public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
    // 클라이언트가 전송한 쿠키를 찾아서 삭제
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if ("Authorization".equals(cookie.getName())) {
          // 쿠키의 값을 null로 설정하고 만료 시간을 과거로 설정하여 삭제
          Cookie deleteCookie = new Cookie("Authorization", null);
          deleteCookie.setPath("/");
          deleteCookie.setHttpOnly(true);
          deleteCookie.setSecure(true); // HTTPS 사용 시 true로 설정
          deleteCookie.setMaxAge(0);
          response.addCookie(deleteCookie);
        }
      }
    }
    return ResponseEntity.ok("로그아웃 되었습니다.");
  }

}
