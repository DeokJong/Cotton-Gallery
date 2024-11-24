package com.cottongallery.backend.auth.service.command.impl;

import com.cottongallery.backend.auth.domain.Account;
import com.cottongallery.backend.auth.dto.account.request.AccountCreateRequest;
import com.cottongallery.backend.auth.dto.account.request.AccountUpdateEmailRequest;
import com.cottongallery.backend.auth.dto.account.request.AccountUpdatePasswordRequest;
import com.cottongallery.backend.auth.exception.account.UsernameAlreadyExistsException;
import com.cottongallery.backend.auth.repository.AccountRepository;
import com.cottongallery.backend.auth.service.command.AccountCommandService;
import com.cottongallery.backend.auth.service.query.AccountQueryService;
import com.cottongallery.backend.common.constants.Role;
import com.cottongallery.backend.common.dto.AccountSessionDTO;
import com.cottongallery.backend.order.domain.Address;
import com.cottongallery.backend.order.domain.AddressType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AccountCommandServiceImpl implements AccountCommandService {

    // query
    private final AccountQueryService accountQueryService;

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Long signUp(AccountCreateRequest accountCreateRequest) {

        if (accountQueryService.isUsernameDuplicate(accountCreateRequest.getUsername())) {
            throw new UsernameAlreadyExistsException("이미 존재하는 사용자명입니다. 다른 사용자명을 사용해 주세요.");
        }

        Account account = Account.createAccount(accountCreateRequest.getName(),
                accountCreateRequest.getUsername(),
                passwordEncoder.encode(accountCreateRequest.getPassword()),
                accountCreateRequest.getEmail(),
                accountCreateRequest.getPhoneNumber(),
                Role.ROLE_USER);

        account.addAddress(new Address(accountCreateRequest.getZipcode(),
                accountCreateRequest.getStreet(),
                accountCreateRequest.getDetail(), AddressType.PRIMARY));

        Account savedAccount = accountRepository.save(account);

        log.debug("계정 생성 성공: username={}", account.getUsername());

        return savedAccount.getId();
    }

    @Override
    public void updateEmail(AccountUpdateEmailRequest accountUpdateEmailRequest, AccountSessionDTO accountSessionDTO) {
        Account account = accountQueryService.getAccountEntityByUsername(accountSessionDTO.getUsername());

        account.changeEmail(accountUpdateEmailRequest.getEmail());
    }

    @Override
    public void updatePassword(AccountUpdatePasswordRequest accountUpdatePasswordRequest, AccountSessionDTO accountSessionDTO) {
        Account account = accountQueryService.getAccountEntityByUsername(accountSessionDTO.getUsername());

        account.changePassword(passwordEncoder.encode(accountUpdatePasswordRequest.getPassword()));
    }
}
