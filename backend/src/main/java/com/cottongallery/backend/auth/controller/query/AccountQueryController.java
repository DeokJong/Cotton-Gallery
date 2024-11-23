package com.cottongallery.backend.auth.controller.query;

import com.cottongallery.backend.auth.controller.query.api.AccountQueryApi;
import com.cottongallery.backend.auth.dto.account.response.AccountCheckUsernameResponse;
import com.cottongallery.backend.auth.service.query.AccountQueryService;
import com.cottongallery.backend.common.dto.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AccountQueryController implements AccountQueryApi {

    private final AccountQueryService accountQueryService;

    @Override
    @GetMapping(value = "/public/accounts/check-username", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<AccountCheckUsernameResponse>> checkUsername(@RequestParam("username") String username) {
        Boolean isDuplicated = accountQueryService.isUsernameDuplicate(username);

        AccountCheckUsernameResponse accountCheckUsernameResponse = new AccountCheckUsernameResponse(isDuplicated);

        log.info("사용자 명 중복 체크 요청 완료: username={}, isDuplicated={}", username, isDuplicated);

        return new ResponseEntity<>(Response.createResponse(HttpStatus.OK.value(), "사용자명 중복 체크가 성공적으로 완료되었습니다.", accountCheckUsernameResponse),
                HttpStatus.OK);
    }
}
