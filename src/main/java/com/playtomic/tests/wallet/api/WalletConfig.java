package com.playtomic.tests.wallet.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.playtomic.tests.wallet.service.WalletService;

@Configuration
public class WalletConfig {

    @Bean
    public WalletService walletService() {
        return new WalletService();
    }

    @Bean
    public WalletController walletController(WalletService walletService) {
        return new WalletController(walletService);
    }
}
