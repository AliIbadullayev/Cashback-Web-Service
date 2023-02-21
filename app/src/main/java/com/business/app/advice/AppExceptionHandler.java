package com.business.app.advice;

import com.business.app.exception.RedirectAddException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler(RedirectAddException.class)
    public ResponseEntity<?> handleBusinessLogicException (RuntimeException runtimeException, WebRequest request) {
        return new ResponseEntity<Object>(
                runtimeException.getMessage(), HttpStatus.FORBIDDEN);
    }
}
