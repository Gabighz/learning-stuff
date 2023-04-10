package com.exchange.exchangeassets.transactionhistory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(
        scanBasePackages = {
                "com.exchange.exchangeassets.transactionhistory",
                "com.exchange.exchangeassets.common"
        })
@EnableDiscoveryClient
public class TransactionHistory {

    public static void main(String[] args) {
        SpringApplication.run(TransactionHistory.class, args);
    }
}
