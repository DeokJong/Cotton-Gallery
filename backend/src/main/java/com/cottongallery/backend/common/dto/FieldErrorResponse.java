package com.cottongallery.backend.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class FieldErrorResponse {
    private String field;
    private String code;
    private String message;
}
