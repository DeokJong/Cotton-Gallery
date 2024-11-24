package com.cottongallery.backend.auth.service.command;

import com.cottongallery.backend.auth.dto.account.request.AccountCreateRequest;
import com.cottongallery.backend.auth.exception.account.UsernameAlreadyExistsException;

public interface AccountCommandService {
    /**
     * 사용자 계정을 생성합니다.
     * @param accountCreateRequest 계정 생성에 필요한 데이터
     * @return 생성된 계정의 고유 ID
     * @throws UsernameAlreadyExistsException 사용자명이 이미 사용 중인 경우
     */
    Long signUp(AccountCreateRequest accountCreateRequest);
}
