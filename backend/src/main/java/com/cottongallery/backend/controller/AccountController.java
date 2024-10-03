package com.cottongallery.backend.controller;

import com.cottongallery.backend.controller.validator.AccountCreateRequestValidator;
import com.cottongallery.backend.dto.Response;
import com.cottongallery.backend.dto.account.request.AccountCreateRequest;
import com.cottongallery.backend.dto.account.response.AccountCheckUsernameResponse;
import com.cottongallery.backend.exception.InvalidRequestException;
import com.cottongallery.backend.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static org.springframework.http.MediaType.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AccountController {

    private final AccountService accountService;
    private final AccountCreateRequestValidator accountCreateRequestValidator;

    @InitBinder
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(accountCreateRequestValidator);
    }

    @PostMapping(value = "/sign-up", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> addAccount(@Validated @RequestBody AccountCreateRequest accountCreateRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("회원 가입 요청 값이 올바르지 않습니다. 다시 확인해 주세요.", bindingResult);
        }

        accountService.signUp(accountCreateRequest);

        return new ResponseEntity<>(new Response(LocalDateTime.now(), HttpStatus.CREATED.value(), "회원 가입 성공", null),
                HttpStatus.CREATED);
    }

    @GetMapping("/accounts/check-username")
    public ResponseEntity<Response> checkUsername(@RequestParam String username) {
        Boolean isDuplicated = accountService.isUsernameDuplicate(username);

        AccountCheckUsernameResponse accountCheckUsernameResponse = new AccountCheckUsernameResponse(isDuplicated);

        return new ResponseEntity<>(new Response(LocalDateTime.now(), HttpStatus.OK.value(), "username 중복 체크 성공", accountCheckUsernameResponse),
                HttpStatus.OK);
    }
}
