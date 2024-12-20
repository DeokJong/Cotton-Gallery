package com.cottongallery.backend.item.advice;

import com.cottongallery.backend.common.dto.Response;
import com.cottongallery.backend.item.exception.DiscountNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class DiscountControllerAdvice {

    @ExceptionHandler(DiscountNotFoundException.class)
    public ResponseEntity<Response<?>> handleDiscountNotFoundException(DiscountNotFoundException e) {
        log.warn("[ExceptionHandle]", e);

        return new ResponseEntity<>(Response.createResponseWithoutData(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                HttpStatus.NOT_FOUND);
    }
}
