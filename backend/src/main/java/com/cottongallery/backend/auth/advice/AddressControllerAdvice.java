package com.cottongallery.backend.auth.advice;

import com.cottongallery.backend.auth.exception.address.AddressNotFoundException;
import com.cottongallery.backend.auth.exception.address.PrimaryAddressNotFoundException;
import com.cottongallery.backend.common.dto.Response;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class AddressControllerAdvice {

    @ExceptionHandler(PrimaryAddressNotFoundException.class)
    public ResponseEntity<Response<?>> handlePrimaryAddressNotFoundException(PrimaryAddressNotFoundException e) {
        log.warn("[ExceptionHandle]", e);

        return new ResponseEntity<>(Response.createResponseWithoutData(HttpServletResponse.SC_NOT_FOUND, e.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<Response<?>> handleAddressNotFoundException(AddressNotFoundException e) {
        log.warn("[ExceptionHandle]", e);

        return new ResponseEntity<>(Response.createResponseWithoutData(HttpServletResponse.SC_NOT_FOUND, e.getMessage()),
                HttpStatus.NOT_FOUND);
    }
}
