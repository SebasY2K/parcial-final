package com.colegio.estudiantes.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Entidad JPA que representa a un estudiante matriculado en el colegio dentro de la base de datos.
 *
 * @version 1.0
 */
@Entity
@Table(name = "estudiantes")
@Schema(description = "Datos básicos y académicos de un estudiante del colegio")
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador interno autogenerado", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "El número de identificación es obligatorio")
    @Column(name = "identificacion", nullable = false, unique = true, length = 20)
    @Schema(description = "Documento único de identificación del estudiante", example = "1036684210")
    private String identificacion;

    @NotBlank(message = "El nombre del estudiante es obligatorio")
    @Column(name = "nombres", nullable = false, length = 80)
    @Schema(description = "Nombres completos del estudiante", example = "Valeria")
    private String nombres;

    @NotBlank(message = "El apellido del estudiante es obligatorio")
    @Column(name = "apellidos", nullable = false, length = 80)
    @Schema(description = "Apellidos completos del estudiante", example = "Gómez Restrepo")
    private String apellidos;

    @NotBlank(message = "El grado escolar es obligatorio")
    @Column(name = "grado", nullable = false, length = 20)
    @Schema(description = "Grado y grupo en el que está matriculado", example = "11-A")
    private String grado;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo debe tener un formato válido")
    @Column(name = "correo", nullable = false, length = 120)
    @Schema(description = "Correo institucional del estudiante", example = "valeria.gomez@colegio.edu.co")
    private String correo;

    @NotNull(message = "El promedio académico es obligatorio")
    @Min(value = 0, message = "El promedio no puede ser menor a 0.0")
    @Max(value = 5, message = "El promedio no puede superar 5.0")
    @Column(name = "promedio", nullable = false)
    @Schema(description = "Promedio acumulado actual en escala de 0.0 a 5.0", example = "4.6")
    private Double promedio;

    @Column(name = "fecha_matricula")
    @Schema(description = "Fecha de registro en el sistema", example = "2026-02-03")
    private LocalDate fechaMatricula;

    public Estudiante() {
        this.fechaMatricula = LocalDate.now();
    }

    public Estudiante(String identificacion, String nombres, String apellidos, String grado,
                      String correo, Double promedio, LocalDate fechaMatricula) {
        this.identificacion = identificacion;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.grado = grado;
        this.correo = correo;
        this.promedio = promedio;
        this.fechaMatricula = fechaMatricula != null ? fechaMatricula : LocalDate.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getIdentificacion() { return identificacion; }
    public void setIdentificacion(String identificacion) { this.identificacion = identificacion; }
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public String getGrado() { return grado; }
    public void setGrado(String grado) { this.grado = grado; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public Double getPromedio() { return promedio; }
    public void setPromedio(Double promedio) { this.promedio = promedio; }
    public LocalDate getFechaMatricula() { return fechaMatricula; }
    public void setFechaMatricula(LocalDate fechaMatricula) { this.fechaMatricula = fechaMatricula; }
}
