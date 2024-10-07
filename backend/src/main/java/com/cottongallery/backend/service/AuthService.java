package com.cottongallery.backend.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cottongallery.backend.domain.Account;
import com.cottongallery.backend.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
  private final AccountRepository accountRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  private Boolean isAccount(String username, String rawPassword) {
    Account account = accountRepository.findByUsername(username);

    if (account == null) {
      return false;
    }

    if (!bCryptPasswordEncoder.matches(rawPassword, account.getPassword())) {
      return false;
    }

    return true;
  } 

}
