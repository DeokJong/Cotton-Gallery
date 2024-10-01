package com.cottongallery.backend.dto.account.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreateRequest {

    private String name;

    private String username;

    private String password;

    private String confirmPassword;

    private String email;

    private String phoneNumber;
}
