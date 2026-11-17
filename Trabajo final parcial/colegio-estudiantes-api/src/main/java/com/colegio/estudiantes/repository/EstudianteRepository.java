package com.colegio.estudiantes.repository;

import com.colegio.estudiantes.model.Estudiante;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para las operaciones de persistencia sobre la entidad {@link Estudiante}.
 *
 * @version 1.0
 */
@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

    /**
     * Busca un estudiante específico usando su documento de identificación.
     *
     * @param identificacion número de documento a consultar.
     * @return un {@link Optional} con el estudiante encontrado o vacío si no existe.
     */
    Optional<Estudiante> findByIdentificacion(String identificacion);

    /**
     * Verifica si ya existe un estudiante registrado con ese mismo número de documento.
     *
     * @param identificacion número de documento a validar.
     * @return {@code true} si el documento ya está registrado, {@code false} en caso contrario.
     */
    boolean existsByIdentificacion(String identificacion);
}
