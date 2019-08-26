package com.StartNotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
public class FundooNotesApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(FundooNotesApplication.class, args);
	}

}
