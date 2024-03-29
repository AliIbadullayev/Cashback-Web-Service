package com.business.app.advice;

import com.business.app.exception.*;
import lombok.extern.slf4j.Slf4j;
import com.business.app.exception.IllegalAccessException;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;


@Slf4j
@RestControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler({NotFoundActorException.class, NotFoundRedirectException.class, NotFoundUserException.class,
            NotFoundPaymentMethodException.class, ResourceNotFoundException.class})
    public ResponseEntity<?> handleNotFoundException(RuntimeException runtimeException) {
        log.error("Occurred NotFoundException with message: {}({})", runtimeException.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<Object>(
                runtimeException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({UserAlreadyExistException.class, IllegalPageParametersException.class, NotHandledWithdrawException.class,
            NotHandledPurchaseException.class, ActorAlreadyExistException.class})
    public ResponseEntity<?> handleBadRequestException(RuntimeException runtimeException) {
        log.error("Occurred BadRequestException with message: {}({})", runtimeException.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Object>(
                runtimeException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AuthenticationException.class, JwtAuthenticationException.class, IllegalAccessException.class})
    public ResponseEntity<?> handleAuthException(RuntimeException runtimeException){
        log.error("Occurred AuthException with message: {}({})", runtimeException.getMessage(),  HttpStatus.FORBIDDEN);
        return new ResponseEntity<Object>(
                "runtimeException.getMessage()", HttpStatus.GONE);
    }

    @ExceptionHandler({HttpClientErrorException.class})
    public ResponseEntity<?> handleHttpClientException(HttpClientErrorException exception) {
        log.error("Occurred HTTP client exception with message: {}({})", exception.getResponseBodyAsString(), exception.getStatusCode());
        return new ResponseEntity<Object>(
                exception.getResponseBodyAsString(), exception.getStatusCode());
    }

}
