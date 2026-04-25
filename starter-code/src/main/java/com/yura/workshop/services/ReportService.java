package com.yura.workshop.services;

import com.yura.workshop.repositories.CategoryRepository;
import com.yura.workshop.repositories.ProductRepository;
import net.sf.jasperreports.engine.JRException;

import java.time.LocalDate;

/**
 * Servicio de generación de reportes con JasperReports.
 *
 * TAREA: Implementa los tres métodos usando JasperReports:
 *   1. Cargar el .jrxml con JasperCompileManager.compileReport(stream)
 *   2. Preparar los datos (JRBeanCollectionDataSource o JRMapArrayDataSource)
 *   3. Llenar el reporte con JasperFillManager.fillReport(jasper, params, dataSource)
 *   4. Exportar a PDF con JRPdfExporter y retornar el byte[]
 *
 * Los templates .jrxml están en src/main/resources/reports/.
 * Puedes usar getClass().getResourceAsStream("/reports/<nombre>.jrxml") para cargarlos.
 */
public class ReportService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ReportService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Genera el reporte Maestro-Detalle para una categoría específica.
     *
     * <p>El reporte incluye:
     * <ul>
     *   <li>Maestro: datos de la categoría (nombre, descripción, total de productos)</li>
     *   <li>Detalle: subreporte con la lista de productos de la categoría</li>
     *   <li>Subtotales por categoría y total general de inventario</li>
     * </ul>
     *
     * @param categoryId ID de la categoría a reportar
     * @return bytes del PDF generado
     * @throws JRException si ocurre un error durante la compilación o exportación
     */
    public byte[] generateMasterDetailReport(int categoryId) throws JRException {
        // TODO: Implementa este método
        throw new UnsupportedOperationException("Implementa generateMasterDetailReport");
    }

    /**
     * Genera el reporte de inventario con filtros opcionales.
     *
     * <p>Parámetros opcionales (null = sin filtro):
     * <ul>
     *   <li>from: fecha de inicio (created_at &gt;= from)</li>
     *   <li>to: fecha de fin (created_at &lt;= to)</li>
     *   <li>minStock: stock mínimo (0 = sin filtro)</li>
     * </ul>
     *
     * @param from fecha de inicio del filtro (puede ser null)
     * @param to fecha de fin del filtro (puede ser null)
     * @param minStock stock mínimo (0 = sin filtro)
     * @return bytes del PDF generado
     * @throws JRException si ocurre un error durante la compilación o exportación
     */
    public byte[] generateInventoryReport(LocalDate from, LocalDate to, int minStock) throws JRException {
        // TODO: Implementa este método
        throw new UnsupportedOperationException("Implementa generateInventoryReport");
    }

    /**
     * Genera el reporte de resumen ejecutivo del inventario.
     *
     * <p>El reporte incluye métricas clave en una sola página:
     * <ul>
     *   <li>Total de productos</li>
     *   <li>Valor total del inventario (precio × stock)</li>
     *   <li>Producto más caro y su precio</li>
     *   <li>Producto con mayor stock y su cantidad</li>
     * </ul>
     *
     * @return bytes del PDF generado
     * @throws JRException si ocurre un error durante la compilación o exportación
     */
    public byte[] generateSummaryReport() throws JRException {
        // TODO: Implementa este método
        throw new UnsupportedOperationException("Implementa generateSummaryReport");
    }
}
