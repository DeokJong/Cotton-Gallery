package com.cottongallery.backend.service;

import com.cottongallery.backend.auth.repository.AccountRepository;
import com.cottongallery.backend.auth.service.impl.AccountQueryServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.cottongallery.backend.util.AccountTestData.USERNAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AccountQueryServiceTest {

    @InjectMocks
    private AccountQueryServiceImpl accountQueryService;

    @Mock
    private AccountRepository accountRepository;

    @Test
    @DisplayName("username 중복 확인 - 이미 사용 중인 경우")
    void isUsernameDuplicateReturnTrue() {
        // given
        given(accountRepository
                .existsByUsername(any(String.class)))
                .willReturn(true);

        // when
        Boolean isDuplicated = accountQueryService.isUsernameDuplicate(USERNAME);

        // then
        assertThat(isDuplicated).isTrue();

        verify(accountRepository, times(1)).existsByUsername(any(String.class));
    }

    @Test
    @DisplayName("username 중복 확인 - 사용하지 않는 경우")
    void isUsernameDuplicateReturnFalse() {
        // given
        given(accountRepository
                .existsByUsername(any(String.class)))
                .willReturn(false);

        // when
        Boolean isDuplicated = accountQueryService.isUsernameDuplicate(USERNAME);

        // then
        assertThat(isDuplicated).isFalse();

        verify(accountRepository, times(1)).existsByUsername(any(String.class));
    }
}
