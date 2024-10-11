package com.cottongallery.backend.controller;

import com.cottongallery.backend.controller.validator.AccountCreateRequestValidator;
import com.cottongallery.backend.dto.DataResponse;
import com.cottongallery.backend.dto.Response;
import com.cottongallery.backend.dto.account.request.AccountCreateRequest;
import com.cottongallery.backend.dto.account.response.AccountCheckUsernameResponse;
import com.cottongallery.backend.exception.InvalidRequestException;
import com.cottongallery.backend.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.*;

@Slf4j
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

        log.info("회원 가입 요청 완료: username={}", accountCreateRequest.getUsername());

        return new ResponseEntity<>(new Response(HttpStatus.CREATED.value(), "회원가입이 성공적으로 완료되었습니다."),
                HttpStatus.CREATED);
    }

    @GetMapping("/accounts/check-username")
    public ResponseEntity<DataResponse> checkUsername(@RequestParam String username) {
        Boolean isDuplicated = accountService.isUsernameDuplicate(username);

        AccountCheckUsernameResponse accountCheckUsernameResponse = new AccountCheckUsernameResponse(isDuplicated);

        log.info("사용자 명 중복 체크 요청 완료: username={}, isDuplicated={}", username, isDuplicated);

        return new ResponseEntity<>(new DataResponse(HttpStatus.OK.value(), "사용자명 중복 체크가 성공적으로 완료되었습니다.", accountCheckUsernameResponse),
                HttpStatus.OK);
    }
}
