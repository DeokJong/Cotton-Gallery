package com.cottongallery.backend.auth.service.impl;

import com.cottongallery.backend.auth.domain.Account;
import com.cottongallery.backend.auth.domain.Address;
import com.cottongallery.backend.auth.domain.AddressType;
import com.cottongallery.backend.auth.dto.request.AddressCreateRequest;
import com.cottongallery.backend.auth.repository.AddressRepository;
import com.cottongallery.backend.auth.service.AddressCommandService;
import com.cottongallery.backend.auth.service.AccountQueryService;
import com.cottongallery.backend.auth.service.AddressQueryService;
import com.cottongallery.backend.common.dto.AccountSessionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressCommandServiceImpl implements AddressCommandService {

    private final AccountQueryService accountQueryService;
    private final AddressQueryService addressQueryService;

    private final AddressRepository addressRepository;

    @Override
    public Long createAddress(AddressCreateRequest addressCreateRequest, AccountSessionDTO accountSessionDTO) {
        Account account = accountQueryService.getAccountEntityByUsername(accountSessionDTO.getUsername());

        Address address = Address.createAddress(addressCreateRequest.getZipcode(), addressCreateRequest.getStreet(),
                addressCreateRequest.getDetail(), AddressType.SECONDARY, account);

        Address savedAddress = addressRepository.save(address);

        return savedAddress.getId();
    }

    @Override
    public void updatePrimaryAddressById(Long addressId, AccountSessionDTO accountSessionDTO) {
        Address primaryAddress = addressQueryService.getPrimaryAddressEntityByUsername(accountSessionDTO.getUsername());
        primaryAddress.changeAddressType(AddressType.SECONDARY);

        Address address = addressQueryService.getAddressEntityByIdAndUsername(addressId, accountSessionDTO.getUsername());
        address.changeAddressType(AddressType.PRIMARY);
    }

    @Override
    public void deleteAddress(Long addressId, AccountSessionDTO accountSessionDTO) {
        Address address = addressQueryService.getAddressEntityByIdAndUsername(addressId, accountSessionDTO.getUsername());

        addressRepository.delete(address);
    }
}
