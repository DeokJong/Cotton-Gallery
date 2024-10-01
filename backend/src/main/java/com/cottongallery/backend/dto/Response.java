package com.cottongallery.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    private LocalDateTime timestamp;

    private int status;

    private String message;

    private Object payload;
}
