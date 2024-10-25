package com.cottongallery.backend.auth.utils;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cottongallery.backend.auth.domain.Account;
import com.cottongallery.backend.auth.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AccountDetailsService.class);
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));
        logger.info("AccountDetailsService: {}", account);
        return User.builder()
            .username(account.getUsername())
            .password(account.getPassword())
            .authorities(account.getRole().name())
            .build();
    }
}
