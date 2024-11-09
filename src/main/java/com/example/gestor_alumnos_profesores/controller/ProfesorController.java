package com.example.gestor_alumnos_profesores.controller;

import com.example.gestor_alumnos_profesores.model.Profesor;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("/profesores")
public class ProfesorController {
    private List<Profesor> profesores = new ArrayList<>();

    // GET /profesores 
    @GetMapping
    public List<Profesor> getAllProfesores() {
        return profesores;
    }

    // GET /profesores/{id} - Retrieve a professor by ID
    @GetMapping("/{id}")
    public ResponseEntity<Profesor> getProfesorById(@PathVariable Long id) {
        Optional<Profesor> profesor = profesores.stream().filter(p -> p.getId().equals(id)).findFirst();
        return profesor.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // POST /profesores - Create a new professor
    @PostMapping
    public ResponseEntity<Profesor> createProfesor(@Valid @RequestBody Profesor profesor) {
        if (profesor.getId() == null) {
            profesor.setId(generateId()); // Generates a unique ID
        }
        profesores.add(profesor);
        return ResponseEntity.status(HttpStatus.CREATED).body(profesor);
    }

    // PUT /profesores/{id} - Update a professor by ID
    @PutMapping("/{id}")
    public ResponseEntity<Profesor> updateProfesor(@PathVariable Long id, @Valid @RequestBody Profesor updatedProfesor) {
        Optional<Profesor> existingProfesor = profesores.stream().filter(p -> p.getId().equals(id)).findFirst();
        if (existingProfesor.isPresent()) {
            Profesor profesor = existingProfesor.get();
            profesor.setNombres(updatedProfesor.getNombres());
            profesor.setApellidos(updatedProfesor.getApellidos());
            profesor.setNumeroEmpleado(updatedProfesor.getNumeroEmpleado());
            profesor.setHorasClase(updatedProfesor.getHorasClase());
            return ResponseEntity.ok(profesor);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // DELETE /profesores/{id} - Delete a professor by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfesor(@PathVariable Long id) {
        boolean removed = profesores.removeIf(profesor -> profesor.getId().equals(id));
        return removed ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private Long generateId() {
        return profesores.size() + 1L; // Simple ID generation for testing
    }
}
