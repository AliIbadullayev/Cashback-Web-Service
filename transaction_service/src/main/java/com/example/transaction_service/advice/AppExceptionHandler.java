package com.example.transaction_service.advice;

import com.example.transaction_service.exception.*;
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
            NotHandledPurchaseException.class, TransactionException.class})
    public ResponseEntity<?> handleBadRequestException(RuntimeException runtimeException) {
        return new ResponseEntity<Object>(
                runtimeException.getMessage(), HttpStatus.BAD_REQUEST);
    }


}
