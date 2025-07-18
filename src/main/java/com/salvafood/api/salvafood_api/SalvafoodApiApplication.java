package com.salvafood.api.salvafood_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SalvafoodApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvafoodApiApplication.class, args);
	}

}
