package com.playtomic.tests.wallet.service.impl;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;

import com.playtomic.tests.wallet.service.exception.StripeServiceException;
import com.playtomic.tests.wallet.service.stripe.StripeAmountTooSmallException;
import com.playtomic.tests.wallet.service.stripe.StripeService;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

@SpringBootTest(classes = StripeServiceTest.TestConfig.class)
public class StripeServiceTest {

    static class TestConfig {
    }
    
    @Value("${stripe.simulator.charges-uri}")
    private String chargesValue;

    @Value("${stripe.simulator.refunds-uri}")
    private String refundsValue;

    StripeService s;

    @BeforeEach
    public void setUp() throws URISyntaxException{
        URI chargesURI = new URI(chargesValue);
        URI refundsURI = new URI(refundsValue.replace("{payment_id}", "payment123"));
        s = new StripeService(chargesURI, refundsURI, new RestTemplateBuilder());
    }

    @Test
    public void test_exception() {
        Assertions.assertThrows(StripeAmountTooSmallException.class, () -> {
            s.charge("4242 4242 4242 4242", new BigDecimal(5));
        });
    }

    @Test
    public void test_ok() throws StripeServiceException {
        s.charge("4242 4242 4242 4242", new BigDecimal(15));
    }
}
