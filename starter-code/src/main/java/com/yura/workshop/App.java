package com.yura.workshop;

import com.yura.workshop.repositories.CategoryRepositoryImpl;
import com.yura.workshop.repositories.ProductRepositoryImpl;
import com.yura.workshop.services.ReportService;
import net.sf.jasperreports.engine.JRException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

public class App {

    public static void main(String[] args) throws JRException, IOException {
        ReportService service = new ReportService(
                new ProductRepositoryImpl(),
                new CategoryRepositoryImpl()
        );

        Path outputDir = Path.of("sample-reports");
        Files.createDirectories(outputDir);

        byte[] masterDetail = service.generateMasterDetailReport(1);
        try (FileOutputStream fos = new FileOutputStream(outputDir.resolve("sample-master-detail.pdf").toFile())) {
            fos.write(masterDetail);
        }
        System.out.println("Generated: sample-master-detail.pdf (" + masterDetail.length + " bytes)");

        byte[] inventory = service.generateInventoryReport(null, null, 0);
        try (FileOutputStream fos = new FileOutputStream(outputDir.resolve("sample-inventory.pdf").toFile())) {
            fos.write(inventory);
        }
        System.out.println("Generated: sample-inventory.pdf (" + inventory.length + " bytes)");

        byte[] summary = service.generateSummaryReport();
        try (FileOutputStream fos = new FileOutputStream(outputDir.resolve("sample-summary.pdf").toFile())) {
            fos.write(summary);
        }
        System.out.println("Generated: sample-summary.pdf (" + summary.length + " bytes)");
    }
}
