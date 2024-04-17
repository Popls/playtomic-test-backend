package com.playtomic.tests.wallet.api;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.playtomic.tests.wallet.model.Wallet;
import com.playtomic.tests.wallet.service.WalletService;
import com.playtomic.tests.wallet.service.exception.StripeServiceException;
import com.playtomic.tests.wallet.service.exception.WalletNotFoundException;

@RestController
public class WalletController {
    private Logger log = LoggerFactory.getLogger(WalletController.class);

    @Autowired
    private WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @RequestMapping("/")
    void log() {
        log.info("Logging from /");
    }

    @GetMapping("/{walletId}")
    public ResponseEntity<Wallet> getWallet(@PathVariable String walletId) {
        try{
            return ResponseEntity.ok(walletService.getWalletById(walletId));
        }catch(WalletNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{walletId}/topup")
    public ResponseEntity<Wallet> topUpWallet(@PathVariable String creditCardNumber, @RequestParam BigDecimal amount) {
        try{
            return ResponseEntity.ok(walletService.topUpWallet(creditCardNumber, amount));
        }catch(WalletNotFoundException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }catch(StripeServiceException e){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }
}
