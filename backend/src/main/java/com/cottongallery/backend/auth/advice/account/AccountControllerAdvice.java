package com.cottongallery.backend.auth.advice.account;

import com.cottongallery.backend.auth.exception.account.AccountNotFoundException;
import com.cottongallery.backend.auth.exception.account.UsernameAlreadyExistsException;
import com.cottongallery.backend.common.dto.Response;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class AccountControllerAdvice {

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<Response<?>> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException e) {
        log.warn("[ExceptionHandle]", e);

        return new ResponseEntity<>(Response.createResponseWithoutData(HttpStatus.CONFLICT.value(), e.getMessage()),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Response<?>> handleAccountNotFoundException(AccountNotFoundException e) {
        log.warn("[ExceptionHandle]", e);

        return new ResponseEntity<>(Response.createResponseWithoutData(HttpServletResponse.SC_NOT_FOUND, e.getMessage()),
                HttpStatus.NOT_FOUND);
    }
}
