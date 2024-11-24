package com.cottongallery.backend.auth.service.query;

import com.cottongallery.backend.auth.domain.Account;
import com.cottongallery.backend.auth.exception.account.AccountNotFoundException;

public interface AccountQueryService {
    /**
     * 사용자명이 이미 사용 중인지 확인합니다.
     *
     * @param username 확인할 사용자명
     * @return true: 사용자명이 이미 존재함, false: 사용자명을 사용할 수 있음
     */
    Boolean isUsernameDuplicate(String username);

    /**
     * 사용자명을 통해 해당 사용자의 닉네임을 조회합니다.
     *
     * @param username 닉네임을 조회할 사용자명
     * @return 조회된 사용자의 닉네임
     * @throws AccountNotFoundException 해당 사용자명을 가진 계정이 없는 경우
     */
    String getNameByUsername(String username);

    /**
     * 사용자명을 통해 계정 엔티티를 조회합니다.
     *
     * @param username 계정을 조회할 사용자명
     * @return 조회된 Account 엔티티
     * @throws AccountNotFoundException 해당 사용자명을 가진 계정이 존재하지 않는 경우
     */
    Account getAccountEntityByUsername(String username);
}
