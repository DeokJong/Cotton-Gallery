package com.cottongallery.backend.auth.service;

import com.cottongallery.backend.auth.dto.request.AccountCreateRequest;
import com.cottongallery.backend.auth.dto.request.AccountUpdateEmailRequest;
import com.cottongallery.backend.auth.dto.request.AccountUpdatePasswordRequest;
import com.cottongallery.backend.auth.exception.account.AccountNotFoundException;
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
     * @param accountSessionDTO 현재 로그인한 사용자의 정보
     * @throws AccountNotFoundException 해당 사용자명을 가진 계정이 존재하지 않는 경우
     */
    void updateEmail(AccountUpdateEmailRequest accountUpdateEmailRequest, AccountSessionDTO accountSessionDTO);

    /**
     * 사용자의 비밀번호를 변경합니다.
     *
     * @param accountUpdatePasswordRequest 비밀번호 변경에 필요한 데이터
     * @param accountSessionDTO 현재 로그인한 사용자의 정보
     * @throws AccountNotFoundException 해당 사용자명을 가진 계정이 존재하지 않는 경우
     */
    void updatePassword(AccountUpdatePasswordRequest accountUpdatePasswordRequest, AccountSessionDTO accountSessionDTO);
}
