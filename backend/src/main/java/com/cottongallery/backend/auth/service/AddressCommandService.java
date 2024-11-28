package com.cottongallery.backend.auth.service;

import com.cottongallery.backend.auth.dto.request.AddressCreateRequest;
import com.cottongallery.backend.auth.exception.account.AccountNotFoundException;
import com.cottongallery.backend.auth.exception.address.AddressNotFoundException;
import com.cottongallery.backend.auth.exception.address.PrimaryAddressNotFoundException;
import com.cottongallery.backend.common.dto.AccountSessionDTO;

public interface AddressCommandService {
    /**
     * 새로운 주소를 생성합니다.

     * @param addressCreateRequest 생성할 주소 정보
     * @param accountSessionDTO 요청을 수행하는 사용자 정보
     * @return 생성된 주소의 ID
     * @throws AccountNotFoundException 해당 사용자명을 가진 계정이 존재하지 않는 경우
     */
    Long createAddress(AddressCreateRequest addressCreateRequest, AccountSessionDTO accountSessionDTO);


    /**
     * 주소 ID를 통해 기본(primary) 주소를 업데이트합니다.
     *
     * @param addressId 기본 주소로 설정할 주소 ID
     * @param accountSessionDTO 요청을 수행하는 사용자 정보
     * @throws PrimaryAddressNotFoundException 기본 주소가 존재하지 않는 경우
     * @throws AccountNotFoundException 해당 사용자명을 가진 계정이 존재하지 않는 경우
     * @throws AddressNotFoundException 해당 ID와 사용자명에 해당하는 주소가 없는 경우
     */
    void updatePrimaryAddressById(Long addressId, AccountSessionDTO accountSessionDTO);

    /**
     * 주소 ID를 통해 특정 주소를 삭제합니다.
     *
     * @param addressId 삭제할 주소의 ID
     * @param accountSessionDTO 요청을 수행하는 사용자 정보
     * @throws AddressNotFoundException 해당 ID와 사용자명에 해당하는 주소가 없는 경우
     * @throws AccountNotFoundException 해당 사용자명을 가진 계정이 존재하지 않는 경우
     */
    void deleteAddress(Long addressId, AccountSessionDTO accountSessionDTO);
}
