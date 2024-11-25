package com.cottongallery.backend.auth.service.query.impl;

import com.cottongallery.backend.auth.domain.Account;
import com.cottongallery.backend.auth.domain.Address;
import com.cottongallery.backend.auth.domain.AddressType;
import com.cottongallery.backend.auth.dto.address.response.AddressListResponse;
import com.cottongallery.backend.auth.dto.address.response.AddressResponse;
import com.cottongallery.backend.auth.exception.address.AddressNotFoundException;
import com.cottongallery.backend.auth.exception.address.PrimaryAddressNotFoundException;
import com.cottongallery.backend.auth.repository.AddressRepository;
import com.cottongallery.backend.auth.service.query.AccountQueryService;
import com.cottongallery.backend.auth.service.query.AddressQueryService;
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
                .orElseThrow(PrimaryAddressNotFoundException::new);
    }

    @Override
    public Address getAddressEntityByIdAndCreateBy(Long addressId, String createBy) {
        return addressRepository
                .findByIdAndCreatedBy(addressId, createBy)
                .orElseThrow(AddressNotFoundException::new);
    }

    @Override
    public Address getPrimaryAddressEntityResponseByUsername(String username) {
        Account account = accountQueryService.getAccountEntityByUsername(username);

        return addressRepository
                .findByAddressTypeAndAccount(AddressType.PRIMARY, account)
                .orElseThrow(PrimaryAddressNotFoundException::new);
    }
}
