package com.cottongallery.backend.auth.dto.account.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class AccountCheckUsernameResponse {
    private Boolean isDuplicated;
}
