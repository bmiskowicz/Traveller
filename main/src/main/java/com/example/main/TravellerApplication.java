package com.example.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TravellerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravellerApplication.class, args);
		System.out.println("http://localhost:8080/swagger.html");
	}

}
