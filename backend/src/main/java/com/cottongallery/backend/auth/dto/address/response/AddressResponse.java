package com.cottongallery.backend.auth.dto.address.response;

import com.cottongallery.backend.auth.domain.Address;
import com.cottongallery.backend.auth.domain.AddressType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponse {
    private String zipcode;

    private String street;

    private String detail;

    private AddressType addressType;

    public static AddressResponse fromAddress(Address address) {
        return new AddressResponse(address.getZipcode(), address.getStreet(), address.getDetail(), address.getAddressType());
    }
}
