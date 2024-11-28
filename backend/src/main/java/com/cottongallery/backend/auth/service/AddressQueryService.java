package com.cottongallery.backend.auth.service;

import com.cottongallery.backend.auth.domain.Address;
import com.cottongallery.backend.auth.dto.response.AddressListResponse;
import com.cottongallery.backend.auth.dto.response.AddressResponse;
import com.cottongallery.backend.auth.exception.account.AccountNotFoundException;
import com.cottongallery.backend.auth.exception.address.AddressNotFoundException;
import com.cottongallery.backend.auth.exception.address.PrimaryAddressNotFoundException;

public interface AddressQueryService {
    /**
     * 사용자명을 통해 해당 사용자의 모든 주소를 조회합니다.
     *
     * @param username 주소를 조회할 사용자명
     * @return 주소를 조회할 사용자명
     * @throws AccountNotFoundException 해당 사용자명을 가진 계정이 존재하지 않는 경우
     */
    AddressListResponse getAddressListResponseByAccountUsername(String username);

    /**
     * 사용자명을 통해 기본(primary) 주소를 조회합니다.
     *
     * @param username 기본 주소를 조회할 사용자명
     * @return 조회된 기본 주소 정보
     * @throws PrimaryAddressNotFoundException 기본 주소가 존재하지 않는 경우
     * @throws AccountNotFoundException 해당 사용자명을 가진 계정이 존재하지 않는 경우
     */
    AddressResponse getPrimaryAddressResponseByUsername(String username);

    /**
     * 사용자명을 통해 기본(primary) 주소 엔티티를 조회합니다.
     *
     * @param username 기본 주소를 조회할 사용자명
     * @return 조회된 기본 Address 엔티티
     * @throws PrimaryAddressNotFoundException 기본 주소가 존재하지 않는 경우
     * @throws AccountNotFoundException 해당 사용자명을 가진 계정이 존재하지 않는 경우
     */
    Address getPrimaryAddressEntityByUsername(String username);

    /**
     * 사용자명과 주소 ID를 통해 특정 주소 엔티티를 조회합니다.
     *
     * @param addressId 조회할 주소의 ID
     * @param username 주소를 조회할 사용자명
     * @return 조회된 Address 엔티티
     * @throws AddressNotFoundException 해당 ID와 사용자명에 해당하는 주소가 없는 경우
     * @throws AccountNotFoundException 해당 사용자명을 가진 계정이 존재하지 않는 경우
     */
    Address getAddressEntityByIdAndUsername(Long addressId, String username);
}
