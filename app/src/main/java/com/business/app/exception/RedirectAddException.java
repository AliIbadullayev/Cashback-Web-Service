package com.business.app.exception;

public class RedirectAddException extends RuntimeException {
    public RedirectAddException(String notCorrectUserOrMarketplace) {
        super(notCorrectUserOrMarketplace);
    }
}
