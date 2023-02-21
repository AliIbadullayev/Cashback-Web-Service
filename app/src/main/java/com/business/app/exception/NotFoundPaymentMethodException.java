package com.business.app.exception;

public class NotFoundPaymentMethodException extends RuntimeException {
    public NotFoundPaymentMethodException(String s) {
        super(s);
    }
}
