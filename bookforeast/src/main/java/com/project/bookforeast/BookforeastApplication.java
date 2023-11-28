package com.project.bookforeast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
public class BookforeastApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(BookforeastApplication.class, args);
	}

}
