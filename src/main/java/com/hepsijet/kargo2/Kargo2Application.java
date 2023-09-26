package com.hepsijet.kargo2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Kargo2Application {

	public static void main(String[] args) {
		SpringApplication.run(Kargo2Application.class, args);
	}

}
