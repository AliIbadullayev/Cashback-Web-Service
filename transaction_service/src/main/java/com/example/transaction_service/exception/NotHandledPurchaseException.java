package com.example.transaction_service.exception;


public class NotHandledPurchaseException extends RuntimeException {
    public NotHandledPurchaseException(String s) {
        super(s);
    }
}
