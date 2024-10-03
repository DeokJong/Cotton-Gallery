package com.cottongallery.backend.utils.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class TokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.token-validity}")
    private long tokenValidityInMilliseconds; // 토큰 유효 기간을 환경 변수에서 설정

    // SecretKey를 생성하는 메서드 (Base64 디코딩을 포함)
    private SecretKey getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey); // Base64로 인코딩된 키 사용
        if (keyBytes.length < 32) {
            throw new IllegalArgumentException("The secret key is too short. It must be at least 32 bytes.");
        }
        return Keys.hmacShaKeyFor(keyBytes); // 적절한 크기의 SecretKey 생성
    }

    // JWT 토큰 생성 메서드
    public String createToken(String username) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + tokenValidityInMilliseconds); // 유효 시간 설정

        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(getSigningKey(), SignatureAlgorithm.HS256) // SecretKey를 사용하여 서명
            .compact();
    }

    // JWT 토큰 유효성 검사 메서드
    public boolean validateToken(String token) throws JwtException {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            logger.warn("Expired JWT token: {}", e.getMessage()); // 만료된 토큰에 대한 로그
        } catch (JwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage()); // 그 외 JWT 관련 오류에 대한 로그
        }
        return false;
    }
}
