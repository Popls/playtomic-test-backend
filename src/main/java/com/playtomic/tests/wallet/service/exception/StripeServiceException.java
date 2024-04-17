package com.playtomic.tests.wallet.service.exception;

public class StripeServiceException extends RuntimeException {
    public StripeServiceException(String message) {
        super(message);
    }
}
