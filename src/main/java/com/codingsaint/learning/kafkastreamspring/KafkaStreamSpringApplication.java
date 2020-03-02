package com.codingsaint.learning.kafkastreamspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KafkaStreamSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaStreamSpringApplication.class, args);
	}

}
