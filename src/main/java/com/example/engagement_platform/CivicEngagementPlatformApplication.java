package com.example.engagement_platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class CivicEngagementPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(CivicEngagementPlatformApplication.class, args);
	}

}
