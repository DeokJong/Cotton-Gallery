package com.cottongallery.backend.order.exception;

public class OrderAlreadyCompletedException extends RuntimeException{
    public OrderAlreadyCompletedException() {
        super();
    }

    public OrderAlreadyCompletedException(String message) {
        super(message);
    }

    public OrderAlreadyCompletedException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderAlreadyCompletedException(Throwable cause) {
        super(cause);
    }
}
