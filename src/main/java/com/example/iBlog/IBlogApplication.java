package com.example.iBlog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class IBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(IBlogApplication.class, args);
	}

}
