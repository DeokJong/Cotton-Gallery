package com.cottongallery.backend.auth.service.impl;

import com.cottongallery.backend.auth.domain.Account;
import com.cottongallery.backend.auth.exception.account.AccountNotFoundException;
import com.cottongallery.backend.auth.repository.AccountRepository;
import com.cottongallery.backend.auth.service.AccountQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountQueryServiceImpl implements AccountQueryService {

    private final AccountRepository accountRepository;

    @Override
    public Boolean isUsernameDuplicate(String username) {
        return accountRepository.existsByUsername(username);
    }

    @Override
    public String getNameByUsername(String username) {
        return accountRepository
                .findByUsername(username)
                .map(Account::getName)
                .orElseThrow(() -> new AccountNotFoundException("사용자 이름 " + username + "에 해당하는 계정을 찾을 수 없습니다."));
    }

    @Override
    public Account getAccountEntityByUsername(String username) {
        return accountRepository
                .findByUsername(username)
                .orElseThrow(() -> new AccountNotFoundException("사용자 이름 " + username + "에 해당하는 계정을 찾을 수 없습니다."));
    }
}
