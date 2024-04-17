package com.playtomic.tests.wallet.service.exception;

public class WalletInsufficientFoundsException extends RuntimeException {
    public WalletInsufficientFoundsException(String message) {
        super(message);
    }
}
