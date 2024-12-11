package com.example.gestor_alumnos_profesores.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gestor_alumnos_profesores.model.Alumno;

public interface AlumnoRepository extends JpaRepository<Alumno, Integer> {

}
