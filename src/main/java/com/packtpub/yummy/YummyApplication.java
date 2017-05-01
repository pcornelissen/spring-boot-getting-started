package com.packtpub.yummy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class YummyApplication {

	public static void main(String[] args) {

		SpringApplication.run(YummyApplication.class, args);
		System.out.println("I am started");
	}
}
