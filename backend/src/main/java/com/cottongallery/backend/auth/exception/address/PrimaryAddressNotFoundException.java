package com.cottongallery.backend.auth.exception.address;

public class PrimaryAddressNotFoundException extends RuntimeException{
    public PrimaryAddressNotFoundException() {
        super();
    }

    public PrimaryAddressNotFoundException(String message) {
        super(message);
    }

    public PrimaryAddressNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PrimaryAddressNotFoundException(Throwable cause) {
        super(cause);
    }
}
