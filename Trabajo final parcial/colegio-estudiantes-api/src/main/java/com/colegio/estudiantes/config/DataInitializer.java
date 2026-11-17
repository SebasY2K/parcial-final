package com.colegio.estudiantes.config;

import com.colegio.estudiantes.model.Estudiante;
import com.colegio.estudiantes.repository.EstudianteRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Inicializador de datos de prueba para poblar la base de datos al arrancar la aplicación.
 *
 * @version 1.0
 */
@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner cargarDatosIniciales(EstudianteRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.saveAll(List.of(
                        new Estudiante("1036684210", "Valeria", "Gómez Restrepo", "11-A",
                                "valeria.gomez@colegio.edu.co", 4.6, LocalDate.of(2026, 1, 22)),
                        new Estudiante("1037912450", "Mateo", "Cardona Vélez", "10-B",
                                "mateo.cardona@colegio.edu.co", 4.2, LocalDate.of(2026, 1, 25)),
                        new Estudiante("1035448901", "Salomé", "Ospina Arango", "11-A",
                                "salome.ospina@colegio.edu.co", 4.8, LocalDate.of(2026, 1, 28)),
                        new Estudiante("1038105329", "Juan José", "Montoya Ríos", "9-C",
                                "juan.montoya@colegio.edu.co", 3.9, LocalDate.of(2026, 2, 2)),
                        new Estudiante("1036220194", "Camila", "Betancur Londoño", "10-A",
                                "camila.betancur@colegio.edu.co", 4.4, LocalDate.of(2026, 2, 5))
                ));
            }
        };
    }
}
