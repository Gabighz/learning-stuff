package com.exchange.exchangeassets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class ExchangeassetsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExchangeassetsApplication.class, args);
	}

}
