package com.warehouse.demo.demospringapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoSpringAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoSpringAppApplication.class, args);
		System.out.println("Demo application has been launched successfully");		
	}
}
