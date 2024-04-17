package com.playtomic.tests.wallet.service.stripe;

import com.playtomic.tests.wallet.service.exception.StripeServiceException;

public class StripeAmountTooSmallException extends StripeServiceException {

    public StripeAmountTooSmallException() {
        super("");
    }

    public StripeAmountTooSmallException(String message) {
        super(message);
    }
}
