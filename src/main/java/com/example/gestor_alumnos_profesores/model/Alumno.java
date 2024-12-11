package com.example.gestor_alumnos_profesores.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Alumno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre es requerido")
    private String nombres;

    @NotNull(message = "El apellido es requerido")
    @NotBlank(message = "El apellido es requerido")
    private String apellidos;

    @NotNull(message = "La matricula es necesaria")
    @Pattern(regexp = "^[A-Za-z][0-9]*$", message = "La matrícula debe comenzar con una letra")
    private String matricula;

    @NotNull(message = "El promedio es requerido")
    @PositiveOrZero(message = "El promedio debe ser un número positivo")
    private Double promedio;

    private String fotoPerfilUrl;

    // Getters y setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Double getPromedio() {
        return promedio;
    }

    public void setPromedio(Double promedio) {
        this.promedio = promedio;
    }

    public String getFotoPerfilUrl() {
        return fotoPerfilUrl;
     }
  
     public void setFotoPerfilUrl(String fotoPerfilUrl) {
        this.fotoPerfilUrl = fotoPerfilUrl;
     }
}
