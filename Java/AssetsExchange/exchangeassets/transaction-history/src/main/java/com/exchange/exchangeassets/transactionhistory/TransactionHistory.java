package com.exchange.exchangeassets.transactionhistory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {
                "com.exchange.exchangeassets.transactionhistory",
                "com.exchange.exchangeassets.common"
        })
public class TransactionHistory {

    public static void main(String[] args) {
        SpringApplication.run(TransactionHistory.class, args);
    }
}
