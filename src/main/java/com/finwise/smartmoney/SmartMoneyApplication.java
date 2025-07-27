package com.finwise.smartmoney;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SmartMoneyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartMoneyApplication.class, args);
	}

}
