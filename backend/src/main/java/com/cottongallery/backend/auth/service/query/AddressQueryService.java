package com.cottongallery.backend.auth.service.query;

import com.cottongallery.backend.auth.domain.Address;
import com.cottongallery.backend.auth.dto.address.response.AddressListResponse;
import com.cottongallery.backend.auth.dto.address.response.AddressResponse;

public interface AddressQueryService {
    AddressListResponse getAddressListResponseByAccountUsername(String username);
    AddressResponse getPrimaryAddressResponseByUsername(String username);

    Address getPrimaryAddressEntityResponseByUsername(String username);
    Address getAddressEntityByIdAndCreateBy(Long addressId, String createBy);
}
