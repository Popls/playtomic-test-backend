package com.playtomic.tests.wallet.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class Payment {

    @NonNull
    private String id;

    @NonNull
    private BigDecimal amount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
