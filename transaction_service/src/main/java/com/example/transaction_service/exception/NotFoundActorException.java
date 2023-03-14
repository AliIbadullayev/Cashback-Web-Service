package com.example.transaction_service.exception;

public class NotFoundActorException extends RuntimeException{

    public NotFoundActorException(String message) {
        super(message);
    }
}
