# EduCore REST — Sistema de Gestión de Estudiantes (API REST + HATEOAS + Swagger + PDF + Frontend)

Aplicación web tipo REST conectada a base de datos relacional para gestionar la información de los estudiantes de un colegio.

## Endpoints Implementados

1. **Consulta general de los estudiantes (`GET /api/estudiantes`)**: Devuelve el listado completo de estudiantes matriculados aplicando el principio **HATEOAS** (`CollectionModel` y `EntityModel`).
2. **Consultar información de un estudiante por su número de identificación (`GET /api/estudiantes/{identificacion}`)**: Busca un estudiante puntual por su documento de identidad e incluye los enlaces hipermedia `_links` (`self`, `estudiantes`, `reporte-pdf`).
3. **Ingresar nuevos estudiantes a la base de datos (`POST /api/estudiantes`)**: Valida que los campos estén completos y que el documento no se repita antes de guardar en la base de datos.
4. **Exportar reportes básicos en formato PDF (`GET /api/estudiantes/reporte/pdf`)**: Construye y descarga un archivo PDF con la tabla consolidada de estudiantes y el promedio general del colegio usando `OpenPDF`.

## Ejecución del Proyecto

1. Abrir una terminal en la carpeta raíz del proyecto y ejecutar:
   ```bash
   mvn spring-boot:run
   ```
2. Abrir en el navegador:
   - **Frontend integrado**: `http://localhost:8080/`
   - **Documentación Swagger UI**: `http://localhost:8080/swagger-ui.html`
   - **Descarga directa del reporte PDF**: `http://localhost:8080/api/estudiantes/reporte/pdf`
