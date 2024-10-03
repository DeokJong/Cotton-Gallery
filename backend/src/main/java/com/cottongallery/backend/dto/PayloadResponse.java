package com.cottongallery.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PayloadResponse extends Response {
    private Object payload;

    public PayloadResponse(int status, String message, Object payload) {
        super(status, message);
        this.payload = payload;
    }
}
