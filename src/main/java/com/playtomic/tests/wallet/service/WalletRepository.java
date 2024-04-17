package com.playtomic.tests.wallet.service;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mongodb.lang.NonNull;
import com.playtomic.tests.wallet.model.Wallet;

public interface WalletRepository extends MongoRepository<Wallet, String>{
    
    Wallet save(Wallet wallet);

    Optional<Wallet> findById(String walletId);
}
