package com.cottongallery.backend.dto.account.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class AccountCheckUsernameResponse {
    private Boolean isDuplicated;
}
