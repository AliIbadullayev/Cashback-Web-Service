package com.example.transaction_service.exception;

public class NotHandledWithdrawException extends RuntimeException {
    public NotHandledWithdrawException(String s) {
        super(s);
    }
}
