package com.cottongallery.backend.order.advice;

import com.cottongallery.backend.common.dto.Response;
import com.cottongallery.backend.order.exception.NotEnoughStockQuantityException;
import com.cottongallery.backend.order.exception.OrderAlreadyCompletedException;
import com.cottongallery.backend.order.exception.OrderNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class OrderControllerAdvice {

    @ExceptionHandler(NotEnoughStockQuantityException.class)
    public ResponseEntity<Response<?>> handleNotEnoughStockQuantityException(NotEnoughStockQuantityException e) {
        log.warn("[ExceptionHandle]", e);

        return new ResponseEntity<>(Response.createResponseWithoutData(HttpStatus.BAD_REQUEST.value(), e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderAlreadyCompletedException.class)
    public ResponseEntity<Response<?>> handleOrderAlreadyCompletedException(OrderAlreadyCompletedException e) {
        log.warn("[ExceptionHandle]", e);

        return new ResponseEntity<>(Response.createResponseWithoutData(HttpStatus.BAD_REQUEST.value(), e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Response<?>> handleOrderNotFoundException(OrderNotFoundException e) {
        log.warn("[ExceptionHandle]", e);

        return new ResponseEntity<>(Response.createResponseWithoutData(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                HttpStatus.NOT_FOUND);
    }
}
