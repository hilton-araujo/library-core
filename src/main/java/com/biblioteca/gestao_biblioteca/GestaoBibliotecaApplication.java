package com.biblioteca.gestao_biblioteca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GestaoBibliotecaApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestaoBibliotecaApplication.class, args);
	}

}
