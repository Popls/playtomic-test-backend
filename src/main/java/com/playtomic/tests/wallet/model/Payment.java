package com.playtomic.tests.wallet.model;

import java.math.BigDecimal;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;

public class Payment {

    @NonNull
    private String id;

    @NonNull
    private BigDecimal amount;

    @JsonCreator
    public Payment(@JsonProperty(value = "amount", required = true) BigDecimal amount) {
        this.id = UUID.randomUUID().toString();
        this.amount = amount;
    }

    @JsonCreator
    public Payment(@JsonProperty(value = "id", required = true) String id) {
        this.id = id;
        this.amount = BigDecimal.ZERO;
    }

    @JsonCreator
    public Payment(@JsonProperty(value = "id", required = true) String id, @JsonProperty(value = "amount", required = true) BigDecimal amount) {
        this.id = id;
        this.amount = amount;
    }

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
