package com.exchange.exchangeassets.tradingengine;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("transaction-history")
public interface TransactionHistoryClient {

    @PostMapping("/transactions")
    void postTransaction(Transaction transaction);
}