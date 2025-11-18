package com.example.athleticore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class AthletiCoreApplication {
	public static void main(String[] args) {
		SpringApplication.run(AthletiCoreApplication.class, args);
	}
}
