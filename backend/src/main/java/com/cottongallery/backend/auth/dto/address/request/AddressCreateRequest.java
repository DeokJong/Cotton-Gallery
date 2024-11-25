package com.cottongallery.backend.auth.dto.address.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AddressCreateRequest {
    private String zipcode;
    private String street;
    private String detail;
}
