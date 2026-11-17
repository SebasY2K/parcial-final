package com.colegio.estudiantes.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.colegio.estudiantes.controller.EstudianteController;
import com.colegio.estudiantes.model.Estudiante;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

/**
 * Ensamblador encargado de aplicar el principio HATEOAS a las respuestas del API.
 * Envuelve cada entidad {@link Estudiante} en un {@link EntityModel} agregando enlaces hipermedia.
 *
 * @version 1.0
 */
@Component
public class EstudianteModelAssembler implements RepresentationModelAssembler<Estudiante, EntityModel<Estudiante>> {

    @Override
    public EntityModel<Estudiante> toModel(Estudiante estudiante) {
        return EntityModel.of(estudiante,
                linkTo(methodOn(EstudianteController.class)
                        .obtenerPorIdentificacion(estudiante.getIdentificacion())).withSelfRel(),
                linkTo(methodOn(EstudianteController.class)
                        .listarEstudiantes()).withRel("estudiantes"),
                linkTo(methodOn(EstudianteController.class)
                        .exportarReportePdf()).withRel("reporte-pdf")
        );
    }
}
