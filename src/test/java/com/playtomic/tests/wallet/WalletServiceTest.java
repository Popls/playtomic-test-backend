package com.playtomic.tests.wallet;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.playtomic.tests.wallet.model.Payment;
import com.playtomic.tests.wallet.model.Wallet;
import com.playtomic.tests.wallet.service.PaymentService;
import com.playtomic.tests.wallet.service.WalletService;
import com.playtomic.tests.wallet.service.exception.StripeServiceException;
import com.playtomic.tests.wallet.service.exception.WalletAlreadyExistsException;
import com.playtomic.tests.wallet.service.exception.WalletInsufficientFoundsException;
import com.playtomic.tests.wallet.service.exception.WalletNotFoundException;
import com.playtomic.tests.wallet.service.repository.WalletRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = WalletServiceTest.TestConfig.class)
public class WalletServiceTest {

    static class TestConfig {
    }

    @Mock
    private PaymentService paymentService;

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private WalletService walletService;

    
    String walletId;
    BigDecimal amount;
    Wallet wallet;
    Payment payment;
    List<Payment> payments;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        walletId = "wallet123";
        amount = BigDecimal.valueOf(100);
        payment = new Payment(UUID.randomUUID().toString(), amount);
        payments = Lists.newArrayList();
        wallet = new Wallet(walletId, amount, payments);
    }

    @Test
    void testGetWalletById() {
        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));
        Wallet actualWallet = walletService.getWalletById(walletId);
        assertEquals(wallet, actualWallet);
    }

    @Test
    void testTopUpWallet() throws WalletNotFoundException, StripeServiceException {
        when(paymentService.charge(walletId, amount)).thenReturn(payment);
        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));
        when(walletRepository.save(wallet)).thenReturn(wallet);
        Wallet updatedWallet = walletService.topUpWallet(walletId, amount);
        assertTrue(updatedWallet.getPayments().contains(payment));
    }

    @Test
    void testPurchases() throws WalletNotFoundException {
        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));
        when(walletRepository.save(wallet)).thenReturn(wallet);
        Wallet updatedWallet = walletService.purchases(walletId, amount);
        assertEquals(updatedWallet.getBalance(), BigDecimal.valueOf(200));
    }

    @Test
    void testRefund() throws WalletNotFoundException, WalletInsufficientFoundsException {
        wallet.addPayment(payment);
        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));
        when(walletRepository.save(wallet)).thenReturn(wallet);
        Wallet refundedWallet = walletService.refund(walletId, payment.getId());
        assertFalse(refundedWallet.getPayments().contains(payment));
    }

    @Test
    void testCreateWallet() throws WalletAlreadyExistsException {
        Wallet wallet = new Wallet(walletId, amount, payments);
        when(walletRepository.findById(walletId)).thenReturn(Optional.empty());
        when(walletRepository.save(wallet)).thenReturn(wallet);
        Wallet createdWallet = walletService.createWallet(wallet);
        assertEquals(wallet, createdWallet);
    }

    @Test
    void testDeleteWallet() {
        walletService.deleteWallet(walletId);
        verify(walletRepository, times(1)).deleteById(walletId);
    }
}
