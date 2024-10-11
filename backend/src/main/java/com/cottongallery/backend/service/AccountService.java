package com.cottongallery.backend.service;

import com.cottongallery.backend.constants.Role;
import com.cottongallery.backend.domain.Account;
import com.cottongallery.backend.domain.Address;
import com.cottongallery.backend.dto.account.request.AccountCreateRequest;
import com.cottongallery.backend.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder  passwordEncoder;

    public Long signUp(AccountCreateRequest accountCreateRequest) {
        Account account = Account.createAccount(accountCreateRequest.getName(),
                accountCreateRequest.getUsername(),
                passwordEncoder.encode(accountCreateRequest.getPassword()),
                accountCreateRequest.getEmail(),
                accountCreateRequest.getPhoneNumber(),
                Role.ROLE_USER);

        account.addAddress(new Address(accountCreateRequest.getZipcode(),
                accountCreateRequest.getStreet(),
                accountCreateRequest.getDetail()));

        accountRepository.save(account);

        return account.getId();
    }
}
