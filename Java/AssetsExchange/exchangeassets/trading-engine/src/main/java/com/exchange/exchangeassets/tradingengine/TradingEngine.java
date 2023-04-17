package com.exchange.exchangeassets.tradingengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class },
        scanBasePackages = {
                "com.exchange.exchangeassets.tradingengine",
                "com.exchange.exchangeassets.common"
        })
public class TradingEngine {

    public static void main(String[] args) {
        SpringApplication.run(TradingEngine.class, args);
    }
}
