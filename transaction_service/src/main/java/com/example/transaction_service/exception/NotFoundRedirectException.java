package com.example.transaction_service.exception;

public class NotFoundRedirectException extends RuntimeException {
    public NotFoundRedirectException(String notCorrectUserOrMarketplace) {
        super(notCorrectUserOrMarketplace);
    }
}
