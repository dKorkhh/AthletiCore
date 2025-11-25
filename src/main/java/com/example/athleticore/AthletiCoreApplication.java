package com.example.athleticore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableScheduling
@EnableCaching
public class AthletiCoreApplication {
	public static void main(String[] args) {
		SpringApplication.run(AthletiCoreApplication.class, args);
	}
}
