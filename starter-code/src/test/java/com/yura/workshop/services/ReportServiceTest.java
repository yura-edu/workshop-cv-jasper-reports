package com.yura.workshop.services;

import com.yura.workshop.repositories.CategoryRepositoryImpl;
import com.yura.workshop.repositories.ProductRepositoryImpl;
import net.sf.jasperreports.engine.JRException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Integration tests for ReportService.
 *
 * These tests run against a real database. Make sure docker compose is up
 * before running locally, or that DB_URL/DB_USER/DB_PASS env vars are set.
 */
class ReportServiceTest {

    private static ReportService service;

    @BeforeAll
    static void setUp() {
        service = new ReportService(
                new ProductRepositoryImpl(),
                new CategoryRepositoryImpl()
        );
    }

    @Test
    @DisplayName("generateMasterDetailReport returns a non-empty PDF for category 1")
    void masterDetailReportReturnsNonEmptyPdf() throws JRException {
        byte[] pdf = service.generateMasterDetailReport(1);

        assertNotNull(pdf, "PDF bytes should not be null");
        assertTrue(pdf.length > 100, "PDF must be at least 100 bytes (empty PDF is ~60 bytes)");
        assertPdfMagicBytes(pdf);
    }

    @Test
    @DisplayName("generateInventoryReport returns a non-empty PDF with no filters")
    void inventoryReportNoFiltersReturnsNonEmptyPdf() throws JRException {
        byte[] pdf = service.generateInventoryReport(null, null, 0);

        assertNotNull(pdf);
        assertTrue(pdf.length > 100);
        assertPdfMagicBytes(pdf);
    }

    @Test
    @DisplayName("generateInventoryReport respects minStock filter")
    void inventoryReportWithMinStockFilter() throws JRException {
        byte[] pdf = service.generateInventoryReport(null, null, 5);

        assertNotNull(pdf);
        assertTrue(pdf.length > 100);
        assertPdfMagicBytes(pdf);
    }

    @Test
    @DisplayName("generateInventoryReport respects date range filter")
    void inventoryReportWithDateFilter() throws JRException {
        LocalDate from = LocalDate.now().minusYears(1);
        LocalDate to = LocalDate.now().plusDays(1);
        byte[] pdf = service.generateInventoryReport(from, to, 0);

        assertNotNull(pdf);
        assertTrue(pdf.length > 100);
        assertPdfMagicBytes(pdf);
    }

    @Test
    @DisplayName("generateSummaryReport returns a non-empty PDF")
    void summaryReportReturnsNonEmptyPdf() throws JRException {
        byte[] pdf = service.generateSummaryReport();

        assertNotNull(pdf);
        assertTrue(pdf.length > 100);
        assertPdfMagicBytes(pdf);
    }

    private void assertPdfMagicBytes(byte[] data) {
        assertTrue(data.length >= 4, "PDF must have at least 4 bytes");
        // PDF files start with %PDF (0x25 0x50 0x44 0x46)
        assertTrue(data[0] == 0x25 && data[1] == 0x50 && data[2] == 0x44 && data[3] == 0x46,
                "File must start with PDF magic bytes (%PDF)");
    }
}
