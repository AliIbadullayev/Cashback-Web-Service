package com.business.app.advice;

import com.business.app.exception.*;
import com.business.app.exception.IllegalAccessException;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler({NotFoundRedirectException.class, NotFoundUserException.class,
            NotFoundPaymentMethodException.class, ResourceNotFoundException.class})
    public ResponseEntity<?> handleNotFoundException(RuntimeException runtimeException) {
        return new ResponseEntity<Object>(
                runtimeException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({UserAlreadyExistException.class, IllegalPageParametersException.class, NotHandledWithdrawException.class,
            NotHandledPurchaseException.class, ActorAlreadyExistException.class})
    public ResponseEntity<?> handleBadRequestException(RuntimeException runtimeException) {
        return new ResponseEntity<Object>(
                runtimeException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AuthenticationException.class, JwtAuthenticationException.class, IllegalAccessException.class})
    public ResponseEntity<?> handleAuthException(RuntimeException runtimeException){
        return new ResponseEntity<Object>(
                runtimeException.getMessage(), HttpStatus.FORBIDDEN);
    }
}
