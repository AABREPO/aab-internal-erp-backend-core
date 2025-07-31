package com.example.Dashboard2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Dashboard2Application {

	public static void main(String[] args) {
		SpringApplication.run(Dashboard2Application.class, args);
	}
}