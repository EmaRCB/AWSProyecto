package com.example.gestor_alumnos_profesores.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.example.gestor_alumnos_profesores.model.Alumno;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<Alumno> getAlumnoById(@PathVariable Long id) {
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
    public ResponseEntity<Alumno> updateAlumno(@PathVariable Long id, @Valid @RequestBody Alumno updatedAlumno) {
        Optional<Alumno> existingAlumno = alumnos.stream().filter(a -> a.getId().equals(id)).findFirst();
        if (existingAlumno.isPresent()) {
            Alumno alumno = existingAlumno.get();
            alumno.setNombres(updatedAlumno.getNombres());
            alumno.setApellidos(updatedAlumno.getApellidos());
            alumno.setMatricula(updatedAlumno.getMatricula());
            alumno.setPromedio(updatedAlumno.getPromedio());
            return new ResponseEntity<>(alumno, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlumno(@PathVariable Long id) {
        boolean removed = alumnos.removeIf(alumno -> alumno.getId().equals(id));
        return removed ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    private Long generateId() {
        return alumnos.size() + 1L; // Simple ID generation, replace with better logic if needed
    }
}
