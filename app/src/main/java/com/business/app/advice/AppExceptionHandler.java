package com.business.app.advice;

import com.business.app.exception.NotFoundRedirectException;
import com.business.app.exception.NotHandledPurchaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler({NotFoundRedirectException.class, NotHandledPurchaseException.class})
    public ResponseEntity<?> handleNotFoundException (RuntimeException runtimeException) {
        return new ResponseEntity<Object>(
                runtimeException.getMessage(), HttpStatus.NOT_FOUND);
    }
}
