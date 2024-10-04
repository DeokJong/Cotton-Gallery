package com.cottongallery.backend.advice;

import com.cottongallery.backend.dto.PayloadResponse;
import com.cottongallery.backend.dto.Response;
import com.cottongallery.backend.exception.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<Response> handleInvalidRequestException(InvalidRequestException e) {
        log.warn("[ExceptionHandle]", e);

        List<Map<String, String>> errors = e.getBindingResult().getFieldErrors().stream().map(fieldError -> {
            Map<String, String> errorMap = new HashMap<>();

            errorMap.put("field", fieldError.getField());
            errorMap.put("code", fieldError.getCode());
            errorMap.put("message", fieldError.getDefaultMessage());

            return errorMap;

        }).toList();

        return new ResponseEntity<>(new PayloadResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(), errors),
                HttpStatus.BAD_REQUEST);
    }
}
