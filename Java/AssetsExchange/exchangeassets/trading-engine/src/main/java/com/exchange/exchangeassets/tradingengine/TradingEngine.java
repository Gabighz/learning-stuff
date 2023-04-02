package com.exchange.exchangeassets.tradingengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class },
        scanBasePackages = "com.exchange.exchangeassets.common")
@ComponentScan(value = {"com.exchange.exchangeassets.common"}, basePackageClasses = {TradingEngineController.class})
@EnableDiscoveryClient
public class TradingEngine {

    public static void main(String[] args) {
        SpringApplication.run(TradingEngine.class, args);
    }
}
