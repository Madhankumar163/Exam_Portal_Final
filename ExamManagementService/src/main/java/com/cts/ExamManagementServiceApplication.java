package com.cts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ExamManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExamManagementServiceApplication.class, args);
	}

}
