package com.colegio.estudiantes.service;

import com.colegio.estudiantes.model.Estudiante;
import com.colegio.estudiantes.repository.EstudianteRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio que concentra la lógica de negocio para gestionar los estudiantes del colegio.
 *
 * @version 1.0
 */
@Service
public class EstudianteService {

    private final EstudianteRepository estudianteRepository;

    public EstudianteService(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    @Transactional(readOnly = true)
    public List<Estudiante> obtenerTodos() {
        return estudianteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Estudiante> buscarPorIdentificacion(String identificacion) {
        return estudianteRepository.findByIdentificacion(identificacion);
    }

    @Transactional
    public Estudiante guardarEstudiante(Estudiante nuevoEstudiante) {
        if (estudianteRepository.existsByIdentificacion(nuevoEstudiante.getIdentificacion())) {
            throw new IllegalArgumentException(
                    "Ya se encuentra registrado un estudiante con la identificación: "
                            + nuevoEstudiante.getIdentificacion());
        }
        if (nuevoEstudiante.getFechaMatricula() == null) {
            nuevoEstudiante.setFechaMatricula(LocalDate.now());
        }
        return estudianteRepository.save(nuevoEstudiante);
    }
}
