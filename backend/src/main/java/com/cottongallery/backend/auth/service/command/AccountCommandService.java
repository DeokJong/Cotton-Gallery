package com.cottongallery.backend.auth.service.command;

import com.cottongallery.backend.auth.dto.account.request.AccountCreateRequest;

public interface AccountCommandService {
    Long signUp(AccountCreateRequest accountCreateRequest);
}
