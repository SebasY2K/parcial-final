package com.colegio.estudiantes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal que inicia la aplicacion Spring Boot para la gestion de estudiantes del colegio.
 *
 * @version 1.0
 */
@SpringBootApplication
public class ColegioApplication {

    /**
     * Punto de entrada principal de la aplicacion Java.
     *
     * @param args argumentos de linea de comandos.
     */
    public static void main(String[] args) {
        SpringApplication.run(ColegioApplication.class, args);
    }
}
