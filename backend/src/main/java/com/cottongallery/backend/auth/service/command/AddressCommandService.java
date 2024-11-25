package com.cottongallery.backend.auth.service.command;

import com.cottongallery.backend.auth.dto.address.request.AddressCreateRequest;
import com.cottongallery.backend.common.dto.AccountSessionDTO;

public interface AddressCommandService {
    Long createAddress(AddressCreateRequest addressCreateRequest, AccountSessionDTO accountSessionDTO);
    void updatePrimaryAddressById(Long addressId, AccountSessionDTO accountSessionDTO);
    void deleteAddress(Long addressId, AccountSessionDTO accountSessionDTO);
}
