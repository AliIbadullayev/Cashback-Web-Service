package com.example.transaction_service.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ErrorHandler;


@Slf4j
@Service
public class AppExceptionHandler implements ErrorHandler {
    @Override
    public void handleError(Throwable t) {
        log.error(t.getCause().getMessage());
    }
}