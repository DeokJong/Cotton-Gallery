package com.cottongallery.backend.advice.account;

import com.cottongallery.backend.controller.AccountController;
import com.cottongallery.backend.dto.Response;
import com.cottongallery.backend.exception.account.UsernameAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice(assignableTypes = AccountController.class)
public class AccountControllerAdvice {

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<Response> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException e) {
        return new ResponseEntity<>(new Response(LocalDateTime.now(), HttpStatus.CONFLICT.value(), e.getMessage(), null),
                HttpStatus.CONFLICT);
    }
}
