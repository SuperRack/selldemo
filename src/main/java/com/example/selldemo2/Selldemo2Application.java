package com.example.selldemo2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Selldemo2Application {

	public static void main(String[] args) {
		SpringApplication.run(Selldemo2Application.class, args);
	}
}
