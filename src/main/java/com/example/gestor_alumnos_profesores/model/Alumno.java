package com.example.gestor_alumnos_profesores.model;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Alumno {
     private Long id;

    @NotBlank(message = "Nombres is required")
    private String nombres;

    @NotBlank(message = "Apellidos is required")
    private String apellidos;

    @NotNull(message = "Matricula is required")
    @Pattern(regexp = "^[A-Z0-9]+$", message = "Matricula must contain only uppercase letters and digits")
    private String matricula;

    @NotNull(message = "Promedio is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Promedio must be between 0 and 10")
    @DecimalMax(value = "10.0", inclusive = true, message = "Promedio must be between 0 and 10")
    private Double promedio;

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
}
