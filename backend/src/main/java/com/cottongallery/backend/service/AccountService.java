package com.cottongallery.backend.service;

import com.cottongallery.backend.constants.Role;
import com.cottongallery.backend.domain.Account;
import com.cottongallery.backend.dto.account.request.AccountCreateRequest;
import com.cottongallery.backend.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long signUp(AccountCreateRequest accountCreateRequest) {
        Account account = Account.createAccount(accountCreateRequest.getName(),
                accountCreateRequest.getUsername(),
                bCryptPasswordEncoder.encode(accountCreateRequest.getPassword()),
                accountCreateRequest.getEmail(),
                accountCreateRequest.getPhoneNumber(),
                Role.USER);

        accountRepository.save(account);

        return account.getId();
    }
}
