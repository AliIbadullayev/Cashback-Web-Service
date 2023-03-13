package com.example.transaction_service.exception;

public class IllegalPageParametersException extends RuntimeException {

    public IllegalPageParametersException(String message) {
        super(message);
    }
}
