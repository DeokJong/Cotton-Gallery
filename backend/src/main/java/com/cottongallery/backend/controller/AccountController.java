package com.cottongallery.backend.controller;

import com.cottongallery.backend.dto.Response;
import com.cottongallery.backend.dto.account.request.AccountCreateRequest;
import com.cottongallery.backend.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static org.springframework.http.MediaType.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AccountController {

    private final AccountService accountService;

    @PostMapping(value = "/sign-up", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> addAccount(@RequestBody AccountCreateRequest accountCreateRequest) {
        accountService.signUp(accountCreateRequest);

        return new ResponseEntity<>(new Response(LocalDateTime.now(), 201, "회원 가입 성공", null),
                HttpStatus.CREATED);
    }
}
