package com.colegio.estudiantes.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.colegio.estudiantes.hateoas.EstudianteModelAssembler;
import com.colegio.estudiantes.model.Estudiante;
import com.colegio.estudiantes.service.EstudianteService;
import com.colegio.estudiantes.service.ReportePdfService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST principal que expone los cuatro endpoints exigidos para la gestión de estudiantes.
 * Implementa respuestas con enlaces HATEOAS y anotaciones de OpenAPI/Swagger para su documentación.
 *
 * @version 1.0
 */
@RestController
@RequestMapping("/api/estudiantes")
@CrossOrigin(origins = "*")
@Tag(name = "Estudiantes", description = "Endpoints REST con HATEOAS para consultar, registrar y exportar estudiantes")
public class EstudianteController {

    private final EstudianteService estudianteService;
    private final ReportePdfService reportePdfService;
    private final EstudianteModelAssembler assembler;

    public EstudianteController(EstudianteService estudianteService,
                                ReportePdfService reportePdfService,
                                EstudianteModelAssembler assembler) {
        this.estudianteService = estudianteService;
        this.reportePdfService = reportePdfService;
        this.assembler = assembler;
    }

    @GetMapping
    @Operation(
            summary = "1. Consulta general de los estudiantes",
            description = "Retorna el listado completo de estudiantes matriculados junto con sus enlaces HATEOAS."
    )
    @ApiResponse(responseCode = "200", description = "Listado de estudiantes obtenido correctamente")
    public ResponseEntity<CollectionModel<EntityModel<Estudiante>>> listarEstudiantes() {
        List<EntityModel<Estudiante>> estudiantesModel = estudianteService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Estudiante>> coleccion = CollectionModel.of(
                estudiantesModel,
                linkTo(methodOn(EstudianteController.class).listarEstudiantes()).withSelfRel(),
                linkTo(methodOn(EstudianteController.class).exportarReportePdf()).withRel("exportar-reporte-pdf")
        );

        return ResponseEntity.ok(coleccion);
    }

    @GetMapping("/{identificacion}")
    @Operation(
            summary = "2. Consultar estudiante por número de identificación",
            description = "Busca un estudiante por su documento de identidad y devuelve su información con enlaces HATEOAS."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estudiante encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "No existe un estudiante con esa identificación")
    })
    public ResponseEntity<?> obtenerPorIdentificacion(
            @Parameter(description = "Número de identificación del estudiante", example = "1036684210")
            @PathVariable String identificacion) {

        return estudianteService.buscarPorIdentificacion(identificacion)
                .<ResponseEntity<?>>map(estudiante -> ResponseEntity.ok(assembler.toModel(estudiante)))
                .orElseGet(() -> {
                    Map<String, Object> error = new HashMap<>();
                    error.put("mensaje", "No se encontró ningún estudiante con la identificación: " + identificacion);
                    error.put("estado", 404);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
                });
    }

    @PostMapping
    @Operation(
            summary = "3. Ingresar nuevos estudiantes a la base de datos",
            description = "Registra un nuevo estudiante en la base de datos validando que su identificación sea única."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Estudiante registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o número de identificación duplicado")
    })
    public ResponseEntity<?> registrarEstudiante(@Valid @RequestBody Estudiante estudiante) {
        try {
            Estudiante guardado = estudianteService.guardarEstudiante(estudiante);
            EntityModel<Estudiante> resource = assembler.toModel(guardado);

            return ResponseEntity
                    .created(resource.getRequiredLink(IanaLinkRelations.SELF).toUri())
                    .body(resource);
        } catch (IllegalArgumentException ex) {
            Map<String, Object> error = new HashMap<>();
            error.put("mensaje", ex.getMessage());
            error.put("estado", 400);
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping(value = "/reporte/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    @Operation(
            summary = "4. Exportar reportes básicos en formato PDF",
            description = "Genera un documento PDF con la tabla consolidada de los estudiantes registrados en la base de datos."
    )
    @ApiResponse(responseCode = "200", description = "Reporte PDF generado correctamente")
    public ResponseEntity<byte[]> exportarReportePdf() {
        List<Estudiante> estudiantes = estudianteService.obtenerTodos();
        byte[] pdfBytes = reportePdfService.generarReporteEstudiantes(estudiantes);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "reporte_estudiantes_colegio.pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}
