package com.cottongallery.backend.auth.controller.validator;

import lombok.extern.slf4j.Slf4j;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.cottongallery.backend.auth.dto.request.AccountCreateRequest;

@Slf4j
@Component
public class AccountCreateRequestValidator implements Validator {

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return AccountCreateRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        AccountCreateRequest accountCreateRequest = (AccountCreateRequest) target;

        if (!errors.hasErrors()) {
            if (!accountCreateRequest.getPassword().equals(accountCreateRequest.getConfirmPassword())) {
                errors.rejectValue("confirmPassword", "MisMatch");
            }
        }
    }
}
