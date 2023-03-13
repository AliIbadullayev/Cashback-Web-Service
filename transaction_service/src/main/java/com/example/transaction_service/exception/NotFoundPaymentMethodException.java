package com.example.transaction_service.exception;

public class NotFoundPaymentMethodException extends RuntimeException {
    public NotFoundPaymentMethodException(String s) {
        super(s);
    }
}
