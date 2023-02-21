package com.business.app.exception;


public class NotHandledPurchaseException extends RuntimeException {
    public NotHandledPurchaseException(String s) {
        super(s);
    }
}
