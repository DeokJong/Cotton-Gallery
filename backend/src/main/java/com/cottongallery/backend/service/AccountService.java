package com.cottongallery.backend.service;

import com.cottongallery.backend.constants.Role;
import com.cottongallery.backend.domain.Account;
import com.cottongallery.backend.domain.Address;
import com.cottongallery.backend.dto.account.request.AccountCreateRequest;
import com.cottongallery.backend.exception.account.UsernameAlreadyExistsException;
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

        if (isUsernameDuplicate(accountCreateRequest.getUsername())) {
            throw new UsernameAlreadyExistsException("이미 존재하는 유저네임입니다. 다른 유저네임을 사용해 주세요.");
        }

        Account account = Account.createAccount(accountCreateRequest.getName(),
                accountCreateRequest.getUsername(),
                bCryptPasswordEncoder.encode(accountCreateRequest.getPassword()),
                accountCreateRequest.getEmail(),
                accountCreateRequest.getPhoneNumber(),
                Role.USER);

        account.addAddress(new Address(accountCreateRequest.getZipcode(),
                accountCreateRequest.getStreet(),
                accountCreateRequest.getDetail()));

        accountRepository.save(account);

        return account.getId();
    }

    public Boolean isUsernameDuplicate(String username) {
        return accountRepository.existsByUsername(username);
    }
}
