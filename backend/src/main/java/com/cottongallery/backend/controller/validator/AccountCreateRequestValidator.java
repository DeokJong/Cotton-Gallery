package com.cottongallery.backend.controller.validator;

import com.cottongallery.backend.dto.account.request.AccountCreateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Slf4j
@Component
public class AccountCreateRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return AccountCreateRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AccountCreateRequest accountCreateRequest = (AccountCreateRequest) target;

        if (!errors.hasErrors()) {
            if (!accountCreateRequest.getPassword().equals(accountCreateRequest.getConfirmPassword())) {
                errors.rejectValue("confirmPassword", "MisMatch");
            }
        }
    }
}
