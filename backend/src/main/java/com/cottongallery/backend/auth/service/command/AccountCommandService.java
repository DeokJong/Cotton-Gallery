package com.cottongallery.backend.auth.service.command;

import com.cottongallery.backend.auth.dto.account.request.AccountCreateRequest;
import com.cottongallery.backend.auth.dto.account.request.AccountUpdateEmailRequest;
import com.cottongallery.backend.auth.exception.account.UsernameAlreadyExistsException;
import com.cottongallery.backend.common.dto.AccountSessionDTO;

public interface AccountCommandService {
    /**
     * 사용자 계정을 생성합니다.
     * @param accountCreateRequest 계정 생성에 필요한 데이터
     * @return 생성된 계정의 고유 ID
     * @throws UsernameAlreadyExistsException 사용자명이 이미 사용 중인 경우
     */
    Long signUp(AccountCreateRequest accountCreateRequest);

    /**
     * 사용자의 이메일을 변경합니다.
     *
     * @param accountUpdateEmailRequest 이메일 변경에 필요한 데이터
     */
    void updateEmail(AccountUpdateEmailRequest accountUpdateEmailRequest, AccountSessionDTO accountSessionDTO);
}
