package com.cottongallery.backend.auth.controller.validator;

import com.cottongallery.backend.auth.dto.account.request.AccountUpdatePasswordRequest;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Slf4j
@Component
public class AccountUpdatePasswordRequestValidator implements Validator {
    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return AccountUpdatePasswordRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, Errors errors) {
        AccountUpdatePasswordRequest accountUpdatePasswordRequest = (AccountUpdatePasswordRequest) target;

        if (!errors.hasErrors()) {
            if (!accountUpdatePasswordRequest.getPassword().equals(accountUpdatePasswordRequest.getConfirmPassword())) {
                errors.rejectValue("confirmPassword", "MisMatch");
            }
        }
    }
}
