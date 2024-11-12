package com.example.gestor_alumnos_profesores.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestor_alumnos_profesores.model.Alumno;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/alumnos")
@Validated
public class AlumnosController {

    private final List<Alumno> alumnos = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<Alumno>> getAlumnos() {
        return ResponseEntity.ok(alumnos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alumno> getAlumnoById(@PathVariable Integer id) {
        Optional<Alumno> alumno = alumnos.stream().filter(a -> a.getId().equals(id)).findFirst();
        return alumno.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Alumno> createAlumno(@Valid @RequestBody Alumno alumno) {
        if (alumno.getId() == null) {
            alumno.setId(generateId()); // Assumes a method to generate unique IDs
        }
        alumnos.add(alumno);
        return new ResponseEntity<>(alumno, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alumno> updateAlumno(@PathVariable Integer id, @Valid @RequestBody Alumno updatedAlumno) {
        Optional<Alumno> existingAlumno = alumnos.stream().filter(a -> a.getId().equals(id)).findFirst();
        
        if (existingAlumno.isPresent()) {
            Alumno alumno = existingAlumno.get();
            alumno.setNombres(updatedAlumno.getNombres());
            alumno.setApellidos(updatedAlumno.getApellidos());
            alumno.setMatricula(updatedAlumno.getMatricula());
            alumno.setPromedio(updatedAlumno.getPromedio());
            
            System.out.println("Matricula actualizado: " + updatedAlumno.getMatricula());
            System.out.println("Id actualizado: " + updatedAlumno.getId());

            return new ResponseEntity<>(alumno, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlumno(@PathVariable Integer id) {
        boolean removed = alumnos.removeIf(alumno -> alumno.getId().equals(id));
        return removed ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    private Integer generateId() {
        return alumnos.size() + 1; // Simple ID generation, replace with better logic if needed
    }
}
