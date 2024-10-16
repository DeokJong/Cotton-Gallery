package com.cottongallery.backend.auth.utils;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

/**
 * JWT 인증 필터 클래스
 */
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    // 요청 헤더와 토큰 접두사 상수
    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final TokenProvider tokenProvider;

    /**
     * Request에서 토큰 추출
     * 
     * @param request HTTP 요청
     * @return 추출된 JWT 토큰 문자열 또는 null
     */
    private String resolveToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (AUTHORIZATION_HEADER.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

    /**
     * 필터링 로직 구현
     * 
     * @param request  HTTP 요청
     * @param response HTTP 응답
     * @param filterChain 필터 체인
     * @throws ServletException 서블릿 예외
     * @throws IOException      I/O 예외
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            // 요청에서 토큰 추출
            String token = resolveToken(request);
            logger.info("Extracted JWT token: {}", token);
            // 토큰 유효성 검증 및 인증 객체 설정
            if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
                Authentication authentication = tokenProvider.getAuthentication(token);
                // SecurityContextHolder에 인증 객체 설정
                SecurityContextHolder.getContext().setAuthentication(authentication); 
                logger.info("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(),
                             request.getRequestURI());
            } else {
                logger.info("유효한 JWT 토큰이 없습니다, uri: {}", request.getRequestURI());
            }
        } catch (Exception e) {
            // 예외 발생 시 로깅 및 SecurityContext 초기화
            logger.error("JWT 토큰 인증 과정에서 에러가 발생했습니다: {}", e.getMessage());
            SecurityContextHolder.clearContext();
        }

        // 필터 체인 계속 진행
        filterChain.doFilter(request, response);
    }
}
