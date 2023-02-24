package com.business.app.advice;

import com.business.app.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


//TODO: Назначить разным видам ошибок разные коды возврата
@RestControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler({NotFoundRedirectException.class, NotHandledPurchaseException.class, NotFoundUserException.class,
            NotFoundPaymentMethodException.class, NotHandledWithdrawException.class, UserAlreadyExistException.class,
            ResoureNotFoundException.class, IllegalPageParametersException.class})
    public ResponseEntity<?> handleNotFoundException (RuntimeException runtimeException) {
        return new ResponseEntity<Object>(
                runtimeException.getMessage(), HttpStatus.NOT_FOUND);
    }


}
