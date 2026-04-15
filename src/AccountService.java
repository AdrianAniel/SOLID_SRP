package com.codurance.srp;

import java.time.LocalDate;

public class AccountService {

    private TransactionRepository transactionRepository;
    private Clock clock;

    public AccountService(TransactionRepository transactionRepository, Clock clock) {
        this.transactionRepository = transactionRepository;
        this.clock = clock;
    }

    public void deposit(int amount) {
        transactionRepository.add(new Transaction(clock.today(), amount));
    }


    public void withdraw(int amount) {
        transactionRepository.add(new Transaction(clock.today(), -amount));
    }
}