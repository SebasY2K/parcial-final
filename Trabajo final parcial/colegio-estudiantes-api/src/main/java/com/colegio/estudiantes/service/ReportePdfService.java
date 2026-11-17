package com.colegio.estudiantes.service;

import com.colegio.estudiantes.model.Estudiante;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado de construir el reporte básico en formato PDF (Endpoint 4).
 *
 * @version 1.0
 */
@Service
public class ReportePdfService {

    public byte[] generarReporteEstudiantes(List<Estudiante> estudiantes) {
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        Document documento = new Document(PageSize.A4, 36, 36, 42, 42);

        try {
            PdfWriter.getInstance(documento, salida);
            documento.open();

            Font fuenteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 15, new Color(15, 23, 42));
            Font fuenteSubtitulo = FontFactory.getFont(FontFactory.HELVETICA, 10, new Color(90, 90, 90));
            Font fuenteCabecera = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.WHITE);
            Font fuenteCelda = FontFactory.getFont(FontFactory.HELVETICA, 10, new Color(35, 35, 35));

            Paragraph titulo = new Paragraph("REPORTE CONSOLIDADO DE ESTUDIANTES MATRICULADOS", fuenteTitulo);
            titulo.setAlignment(Element.ALIGN_LEFT);
            documento.add(titulo);

            String fechaActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            Paragraph subtitulo = new Paragraph(
                    "Sistema de Gestión Académica Escolar — API REST | Fecha de emisión: " + fechaActual,
                    fuenteSubtitulo);
            subtitulo.setSpacingAfter(16f);
            documento.add(subtitulo);

            PdfPTable tabla = new PdfPTable(5);
            tabla.setWidthPercentage(100);
            tabla.setWidths(new float[]{2.2f, 3.6f, 1.4f, 3.8f, 1.5f});

            String[] columnas = {"Identificación", "Nombre Completo", "Grado", "Correo Institucional", "Promedio"};
            for (String nombreColumna : columnas) {
                PdfPCell celdaHeader = new PdfPCell(new Phrase(nombreColumna, fuenteCabecera));
                celdaHeader.setBackgroundColor(new Color(30, 27, 75));
                celdaHeader.setPadding(7f);
                celdaHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
                tabla.addCell(celdaHeader);
            }

            double sumaPromedios = 0.0;
            for (Estudiante est : estudiantes) {
                tabla.addCell(crearCelda(est.getIdentificacion(), fuenteCelda));
                tabla.addCell(crearCelda(est.getNombres() + " " + est.getApellidos(), fuenteCelda));
                tabla.addCell(crearCelda(est.getGrado(), fuenteCelda));
                tabla.addCell(crearCelda(est.getCorreo(), fuenteCelda));
                tabla.addCell(crearCelda(String.format("%.1f", est.getPromedio()), fuenteCelda));
                if (est.getPromedio() != null) {
                    sumaPromedios += est.getPromedio();
                }
            }

            documento.add(tabla);

            double promedioGeneral = estudiantes.isEmpty() ? 0.0 : sumaPromedios / estudiantes.size();
            Paragraph resumen = new Paragraph(
                    String.format("
Total de estudiantes registrados: %d   |   Promedio general del colegio: %.2f",
                            estudiantes.size(), promedioGeneral),
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, new Color(30, 30, 30)));
            resumen.setSpacingBefore(12f);
            documento.add(resumen);

        } finally {
            if (documento.isOpen()) {
                documento.close();
            }
        }

        return salida.toByteArray();
    }

    private PdfPCell crearCelda(String texto, Font fuente) {
        PdfPCell celda = new PdfPCell(new Phrase(texto != null ? texto : "", fuente));
        celda.setPadding(6f);
        celda.setBorderColor(new Color(220, 220, 220));
        return celda;
    }
}
