package com.centerpoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.centerpoint")
@EnableAutoConfiguration
public class ServiceHelpDeskApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceHelpDeskApplication.class, args);
	}
}
