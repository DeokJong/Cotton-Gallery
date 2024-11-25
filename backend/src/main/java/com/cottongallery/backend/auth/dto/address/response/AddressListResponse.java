package com.cottongallery.backend.auth.dto.address.response;

import com.cottongallery.backend.auth.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressListResponse {
    private List<AddressResponse> address;

    public static AddressListResponse fromAddress(List<Address> addresses) {
        List<AddressResponse> addressResponses = addresses.stream()
                .map(AddressResponse::fromAddress)
                .toList();
        return new AddressListResponse(addressResponses);
    }
}
