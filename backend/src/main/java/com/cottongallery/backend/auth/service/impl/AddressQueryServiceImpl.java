package com.cottongallery.backend.auth.service.impl;

import com.cottongallery.backend.auth.domain.Account;
import com.cottongallery.backend.auth.domain.Address;
import com.cottongallery.backend.auth.domain.AddressType;
import com.cottongallery.backend.auth.dto.response.AddressListResponse;
import com.cottongallery.backend.auth.dto.response.AddressResponse;
import com.cottongallery.backend.auth.exception.address.AddressNotFoundException;
import com.cottongallery.backend.auth.exception.address.PrimaryAddressNotFoundException;
import com.cottongallery.backend.auth.repository.AddressRepository;
import com.cottongallery.backend.auth.service.AccountQueryService;
import com.cottongallery.backend.auth.service.AddressQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AddressQueryServiceImpl implements AddressQueryService {

    private final AccountQueryService accountQueryService;
    private final AddressRepository addressRepository;

    @Override
    public AddressListResponse getAddressListResponseByAccountUsername(String username) {
        Account account = accountQueryService.getAccountEntityByUsername(username);

        List<Address> addresses = addressRepository.findAllByAccount(account);

        return AddressListResponse.fromAddress(addresses);
    }

    @Override
    public AddressResponse getPrimaryAddressResponseByUsername(String username) {
        Account account = accountQueryService.getAccountEntityByUsername(username);

        return addressRepository
                .findByAddressTypeAndAccount(AddressType.PRIMARY, account)
                .map(AddressResponse::fromAddress)
                .orElseThrow(() -> new PrimaryAddressNotFoundException(
                        "사용자 이름 " + username + "에 대한 기본 주소를 찾을 수 없습니다."
                ));
    }

    @Override
    public Address getAddressEntityByIdAndUsername(Long addressId, String username) {
        Account account = accountQueryService.getAccountEntityByUsername(username);

        return addressRepository
                .findByIdAndAccount(addressId, account)
                .orElseThrow(() -> new AddressNotFoundException(
                        "주소 ID " + addressId + "에 대한 정보를 사용자 " + username + "의 계정에서 찾을 수 없습니다."
                ));
    }

    @Override
    public Address getPrimaryAddressEntityByUsername(String username) {
        Account account = accountQueryService.getAccountEntityByUsername(username);

        return addressRepository
                .findByAddressTypeAndAccount(AddressType.PRIMARY, account)
                .orElseThrow(() -> new PrimaryAddressNotFoundException(
                        "사용자 이름 " + username + "에 대한 기본 주소를 찾을 수 없습니다."
                ));
    }
}
