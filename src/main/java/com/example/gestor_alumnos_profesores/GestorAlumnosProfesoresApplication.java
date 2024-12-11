package com.example.gestor_alumnos_profesores;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.example.gestor_alumnos_profesores.model")
@EnableJpaRepositories(basePackages = "com.example.gestor_alumnos_profesores.repositories")
public class GestorAlumnosProfesoresApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestorAlumnosProfesoresApplication.class, args);
	}

}
