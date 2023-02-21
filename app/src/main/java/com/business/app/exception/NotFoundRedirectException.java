package com.business.app.exception;

public class NotFoundRedirectException extends RuntimeException {
    public NotFoundRedirectException(String notCorrectUserOrMarketplace) {
        super(notCorrectUserOrMarketplace);
    }
}
