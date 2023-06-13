package com.example.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TravellerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravellerApplication.class, args);
		System.out.println("https://localhost:442/swagger.html");
	}

}
