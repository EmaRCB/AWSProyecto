package com.example.gestor_alumnos_profesores.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class Profesor {
    @NotNull(message = "El ID es requerido")
    @NotBlank(message = "El ID es requerido")
    private Long id;

    @NotNull(message = "Número de empleado is requerido")
    @NotBlank(message = "Número de empleado is requerido")
    private String numeroEmpleado;

    @NotNull(message = "Nombres es requerido")
    @NotBlank(message = "Nombres es requerido")
    private String nombres;

    @NotNull(message = "Apellidos es requerido")
    @NotBlank(message = "Apellidos es requerido")
    private String apellidos;

    @NotNull(message = "Horas de clase es requerido")
    @Positive(message = "El número de empleado debe ser un número positivo")
    private Integer horasClase;

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }

    public void setNumeroEmpleado(String numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
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

    public Integer getHorasClase() {
        return horasClase;
    }

    public void setHorasClase(Integer horasClase) {
        this.horasClase = horasClase;
    }
}
