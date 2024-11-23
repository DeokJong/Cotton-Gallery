package com.cottongallery.backend.auth.controller.command;

import com.cottongallery.backend.auth.controller.validator.AccountCreateRequestValidator;
import com.cottongallery.backend.auth.dto.account.request.AccountCreateRequest;
import com.cottongallery.backend.auth.service.command.AccountCommandService;
import com.cottongallery.backend.common.dto.Response;
import com.cottongallery.backend.common.exception.InvalidRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AccountCommandController {

    private final AccountCommandService accountCommandService;
    private final AccountCreateRequestValidator accountCreateRequestValidator;

    @InitBinder
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(accountCreateRequestValidator);
    }

    @PostMapping(value = "/sign-up", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<?>> addAccount(@Validated @RequestBody AccountCreateRequest accountCreateRequest,
                                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("회원 가입 요청 값이 올바르지 않습니다. 다시 확인해 주세요.", bindingResult);
        }

        accountCommandService.signUp(accountCreateRequest);

        log.info("회원 가입 요청 완료: username={}", accountCreateRequest.getUsername());

        return new ResponseEntity<>(Response.createResponseWithoutData(HttpStatus.CREATED.value(), "회원가입이 성공적으로 완료되었습니다."),
                HttpStatus.CREATED);
    }
}
