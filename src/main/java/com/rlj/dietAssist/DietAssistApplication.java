package com.rlj.dietAssist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DietAssistApplication {

	public static void main(String[] args) {
		SpringApplication.run(DietAssistApplication.class, args);
	}

}
