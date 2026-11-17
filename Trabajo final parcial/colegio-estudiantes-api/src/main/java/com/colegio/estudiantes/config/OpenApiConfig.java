package com.colegio.estudiantes.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuracion de la documentacion interactiva con OpenAPI 3 y Swagger UI.
 * Permite visualizar y probar los cuatro endpoints desde el navegador.
 *
 * @version 1.0
 */
@Configuration
public class OpenApiConfig {

    /**
     * Define los metadatos generales que se muestran en el encabezado de Swagger UI.
     *
     * @return instancia configurada de {@link OpenAPI}.
     */
    @Bean
    public OpenAPI configurarOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("EduCore REST — API de Gestión Escolar")
                        .version("1.0.0")
                        .description("Servicio REST para la gestión de estudiantes de un colegio. "
                                + "Implementa los 4 endpoints con soporte HATEOAS, "
                                + "exportación de reportes en PDF e integración con Frontend web."));
    }
}
