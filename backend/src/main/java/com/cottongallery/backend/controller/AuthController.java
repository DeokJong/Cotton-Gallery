package com.cottongallery.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cottongallery.backend.domain.Account;
import com.cottongallery.backend.dto.Response;
import com.cottongallery.backend.dto.auth.AuthRequest;
import com.cottongallery.backend.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {
  private final AccountRepository accountRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @PostMapping("/login")
  public ResponseEntity<Response> login(@RequestBody AuthRequest loginRequestDTO) {

    String currentUsername = loginRequestDTO.getUsername();
    String currentPassword = loginRequestDTO.getPassword();
    Account account = accountRepository.findByUsername(currentUsername);

    if (account == null) {
      return ResponseEntity.badRequest().body(new Response());
    }

    if (!bCryptPasswordEncoder.matches(currentPassword, account.getPassword())) {
      return ResponseEntity.badRequest().body(new Response());
    }

    // 3. JWT 토큰 생성 후 리턴 (추후 구현)
    // String jwtToken = jwtService.generateToken(account);
    // return ResponseEntity.ok(new Response("Login successful", jwtToken));

    return ResponseEntity.ok(new Response());
  }

  @PostMapping("/logout")
  public String logout(@RequestBody String entity) {
    // TODO: process POST request

    return entity;
  }

  @GetMapping("/test-repository")
  public String testRepository(@Param("username") String username) {
    return accountRepository.findByUsername(username).toString();
  }

}
