package com.exchange.exchangeassets.tradingengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class },
        scanBasePackages = {
                "com.exchange.exchangeassets.tradingengine",
                "com.exchange.exchangeassets.common"
        })
@EnableDiscoveryClient
@EnableFeignClients
public class TradingEngine {

    public static void main(String[] args) {
        SpringApplication.run(TradingEngine.class, args);
    }
}
