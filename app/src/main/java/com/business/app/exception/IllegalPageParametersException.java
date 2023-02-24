package com.business.app.exception;

public class IllegalPageParametersException extends RuntimeException{

    public IllegalPageParametersException(String message) {
        super(message);
    }
}
