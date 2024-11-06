package com.cottongallery.backend.item.exception;

public class DiscountNotFoundException extends RuntimeException {
    public DiscountNotFoundException() {
        super();
    }

    public DiscountNotFoundException(String message) {
        super(message);
    }

    public DiscountNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DiscountNotFoundException(Throwable cause) {
        super(cause);
    }
}
