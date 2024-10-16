package com.cottongallery.backend.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cottongallery.backend.auth.dto.auth.AuthRequest;
import com.cottongallery.backend.auth.service.AuthService;
import com.cottongallery.backend.auth.utils.TokenProvider;
import com.cottongallery.backend.common.dto.Response;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {
  private final AuthService authService;
  private final TokenProvider tokenProvider;
  private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

  @PostMapping("/login")
  public ResponseEntity<Response> login(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
    logger.info("Login request for user: {}", authRequest.getUsername());
    String token = authService.login(authRequest);

    // HttpOnly 쿠키에 토큰 설정
    Cookie cookie = new Cookie("Authorization", token);
    cookie.setHttpOnly(true);
    cookie.setPath("/");
    cookie.setSecure(true); // HTTPS 사용 시 true로 설정
    cookie.setMaxAge((int) (tokenProvider.getExpiration(token) / 1000));
    response.addCookie(cookie);

    return new ResponseEntity<>(new Response(HttpStatus.OK.value(), "로그인이 성공적으로 완료되었습니다."),
    HttpStatus.OK);
  }

  @PostMapping("/logout")
  public ResponseEntity<Response> logout(HttpServletRequest request, HttpServletResponse response) {
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
    return new ResponseEntity<>(new Response(HttpStatus.NO_CONTENT.value(), "로그아웃이 성공적으로 완료되었습니다."),
    HttpStatus.NO_CONTENT);
  }

}
