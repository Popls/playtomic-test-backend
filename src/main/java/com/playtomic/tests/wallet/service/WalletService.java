package com.playtomic.tests.wallet.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.playtomic.tests.wallet.model.Payment;
import com.playtomic.tests.wallet.model.Wallet;
import com.playtomic.tests.wallet.service.exception.StripeServiceException;
import com.playtomic.tests.wallet.service.exception.WalletInsufficientFoundsException;
import com.playtomic.tests.wallet.service.exception.WalletNotFoundException;

public class WalletService {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private WalletRepository walletRepository;
    
    public Wallet getWalletById(String walletId) throws WalletNotFoundException {
        Optional<Wallet> walletOptional = walletRepository.findById(walletId);
        return walletOptional.orElseThrow(() -> new WalletNotFoundException("Wallet not found with ID: " + walletId));
    }

    public Wallet topUpWallet(String walletId, BigDecimal amount) throws StripeServiceException, WalletNotFoundException {
        Payment payment = paymentService.charge(walletId, amount);
        Wallet wallet = getWalletById(walletId);
        wallet.addPayment(payment);
        return walletRepository.save(wallet);
    }

    public Wallet purchases(String walletId, BigDecimal amount) throws WalletNotFoundException {
        Payment payment = new Payment(amount);
        Wallet wallet = getWalletById(walletId);
        wallet.addPayment(payment);
        return walletRepository.save(wallet);
    }

    public Wallet refund(String walletId, String paymentId) throws WalletNotFoundException {
        Wallet wallet = getWalletById(walletId);
        wallet.removePayment(paymentId);
        if (wallet.getBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new WalletInsufficientFoundsException(String.format("Wallet %s doesn't have enough founds", walletId));
        }
        paymentService.refund(paymentId);
        return walletRepository.save(wallet);
    }

}
