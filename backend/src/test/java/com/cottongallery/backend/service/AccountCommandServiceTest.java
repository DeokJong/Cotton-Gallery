package com.cottongallery.backend.service;

import com.cottongallery.backend.auth.domain.Account;
import com.cottongallery.backend.auth.exception.account.UsernameAlreadyExistsException;
import com.cottongallery.backend.auth.repository.AccountRepository;
import com.cottongallery.backend.auth.service.impl.AccountCommandServiceImpl;
import com.cottongallery.backend.auth.service.AccountQueryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.cottongallery.backend.util.AccountTestData.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AccountCommandServiceTest {

    @InjectMocks
    private AccountCommandServiceImpl accountCommandService;

    @Mock
    private AccountQueryService accountQueryService;

    @Mock
    private AccountRepository accountRepository;

    @Spy
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    @DisplayName("회원 가입 성공")
    void signUpSuccess() {
        // given
        given(accountRepository.save(any(Account.class))).willReturn(createTestAccountWithId());
        given(accountQueryService.isUsernameDuplicate(any(String.class))).willReturn(false);
        // when
        Long accountId = accountCommandService.signUp(createTestAccountCreateRequest());

        // then
        assertThat(accountId).isEqualTo(ID);

        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    @DisplayName("회원 가입 실패 - 이미 존재하는 사용자명")
    void singUpFailUsernameAlreadyExists() {
        // given
        given(accountQueryService.isUsernameDuplicate(any(String.class))).willReturn(true);

        // when - then
        assertThatThrownBy(() -> accountCommandService.signUp(createTestAccountCreateRequest()))
                .isInstanceOf(UsernameAlreadyExistsException.class);

        verify(accountRepository, times(0)).save(any(Account.class));
    }
}
