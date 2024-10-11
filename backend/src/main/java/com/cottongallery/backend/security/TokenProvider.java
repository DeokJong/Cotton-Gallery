package com.cottongallery.backend.security;

import java.util.Date;
import java.util.stream.Collectors;
import java.security.Key;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;

import jakarta.annotation.PostConstruct;

@Component
public class TokenProvider {
  private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

  private static final String AUTHORITIES_KEY = "auth";

  private final String secret;
  private final long tokenValidityInMilliseconds;
  private final UserDetailsService userDetailsService;

  private Key key;
  private JwtParser jwtParser;

  public TokenProvider(
      @Value("${jwt.secret}") String secret,
      @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds,
      UserDetailsService userDetailsService) {

    if (tokenValidityInSeconds <= 0) {
      throw new IllegalArgumentException("토큰 유효 기간은 0보다 커야 합니다.");
    }

    this.secret = secret;
    this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
    this.userDetailsService = userDetailsService;
  }

  @PostConstruct
  public void init() {
    byte[] keyBytes = Decoders.BASE64.decode(secret);
    this.key = Keys.hmacShaKeyFor(keyBytes);
    this.jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
  }

  /**
   * Authentication 객체의 권한 정보를 이용하여 JWT 토큰을 생성하는 메소드
   * 
   * @param authentication
   * @return JWT 토큰
   */
  public String createToken(Authentication authentication) {
    String authorities = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(","));

    Date now = new Date();
    Date validity = new Date(now.getTime() + this.tokenValidityInMilliseconds);

    return Jwts.builder()
        .setSubject(authentication.getName())
        .claim(AUTHORITIES_KEY, authorities)
        .setIssuedAt(now)
        .setExpiration(validity)
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }

  /**
   * JWT 토큰으로부터 인증 정보를 조회하는 메소드
   * 
   * @param token
   * @return Authentication 객체
   */
  public Authentication getAuthentication(String token) {
    if (!validateToken(token)) {
      return null;
    }

    Claims claims = jwtParser.parseClaimsJws(token).getBody();
    String username = claims.getSubject();


    // UserDetailsService를 사용하여 사용자 정보 로드
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    logger.info("UserDetails: {}", userDetails);

    // Authentication 객체 생성 및 반환
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  // 토큰의 유효성을 검증하는 메소드
  public boolean validateToken(String authToken) {
    logger.info("Validating token: {}", authToken);
    try {
      jwtParser.parseClaimsJws(authToken);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      logger.info("유효하지 않은 JWT 토큰입니다: {}", e.getMessage());
    }
    return false;
  }

  /**
   * 토큰의 남은 유효 시간 (밀리초 단위) 반환
   */
  public long getExpiration(String token) {
    Claims claims = jwtParser.parseClaimsJws(token).getBody();
    Date expiration = claims.getExpiration();
    return expiration.getTime() - new Date().getTime();
  }

}
