package com.playtomic.tests.wallet.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

import java.math.BigDecimal;

import com.mongodb.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Wallet {
    
    @NonNull
    private String id;

    @NonNull
    private BigDecimal balance;

    private List<Payment> payments;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public void addPayment(Payment payment){
        this.payments.add(payment);
        balance = balance.add(payment.getAmount());
    }

    public void removePayment(String paymentId){
        this.payments.removeIf(payment -> payment.getId().equals(paymentId));
    }

}
