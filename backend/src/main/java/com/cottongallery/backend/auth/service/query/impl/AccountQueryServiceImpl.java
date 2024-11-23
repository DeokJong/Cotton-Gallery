package com.cottongallery.backend.auth.service.query.impl;

import com.cottongallery.backend.auth.repository.AccountRepository;
import com.cottongallery.backend.auth.service.query.AccountQueryService;
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
}
