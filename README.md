# Reportes Parametrizados con JasperReports

> **Tipo:** DOCUMENTATION · **Duración estimada:** 240 min · **Nivel:** Intermedio (requiere cv-crud-desktop-git)

## Objetivo

Diseñar 3 templates JasperReports (`.jrxml`) e integrar la generación de reportes PDF y XLSX en el sistema CRUD del taller anterior, incluyendo subreportes, parámetros dinámicos, y totales/subtotales.

## Requisitos previos

- Taller `cv-crud-desktop-git` completado
- [Jaspersoft Studio](https://community.jaspersoft.com/project/jaspersoft-studio) instalado (recomendado para diseñar los `.jrxml` visualmente)
- Docker Desktop (para la base de datos local)
- JDK 21

## Instrucciones

### 1. Prepara el entorno

```bash
git clone <url-de-tu-repositorio>
cd workshop-cv-jasper-reports/starter-code

# Levanta la base de datos con datos de prueba
docker compose up -d

# Verifica que el proyecto compila
mvn compile
```

### 2. Explora el starter

El proyecto ya tiene implementado:
- `DatabaseConnection.java` — pool de conexiones HikariCP
- `CategoryRepositoryImpl.java` y `ProductRepositoryImpl.java` — acceso a datos completo
- `ReportServiceTest.java` — tests de integración que verificarán tu trabajo
- Los 4 archivos `.jrxml` placeholder en `src/main/resources/reports/`

Tu trabajo: implementar `ReportService.java` y reemplazar los `.jrxml` placeholder con templates funcionales.

### 3. Diseña los templates JRXML

Abre Jaspersoft Studio y diseña los 3 reportes requeridos (el 4.º es el subreporte del Maestro-Detalle):

#### 3.1 Reporte Maestro-Detalle (`report-master-detail.jrxml`)
- **Maestro**: categoría (nombre, descripción, total de productos, subtotal de inventario)
- **Detalle**: subreporte con productos de la categoría (nombre, precio, stock)
- El detalle es un subreporte real apuntando a `report-detail-sub.jrxml`
- Totales y subtotales por categoría

#### 3.2 Subreporte de Detalle (`report-detail-sub.jrxml`)
- Lista de productos: nombre, precio unitario, stock, valor total
- Diseñado para ser embebido en el reporte maestro
- Subtotal al final de la sección

#### 3.3 Reporte de Inventario (`report-inventory.jrxml`)
- Parámetros dinámicos: `fechaDesde` (Date), `fechaHasta` (Date), `stockMinimo` (Integer)
- Columnas: nombre, precio, stock, estado (✓ Disponible / ✗ Agotado)
- Pie de página: total de items y valor total del inventario
- Los parámetros son opcionales (null = sin filtro)

#### 3.4 Reporte de Resumen Ejecutivo (`report-summary.jrxml`)
- Una página en formato certificado/resumen
- Métricas: total de productos, valor total del inventario, producto más caro, producto con más stock
- Incluir: logo (puedes usar un placeholder), fecha de generación

### 4. Implementa ReportService.java

Abre `src/main/java/com/yura/workshop/services/ReportService.java` e implementa los 3 métodos:

```java
// Genera el reporte Maestro-Detalle para una categoría
byte[] generateMasterDetailReport(int categoryId) throws JRException

// Genera el reporte de inventario con filtros opcionales
byte[] generateInventoryReport(LocalDate from, LocalDate to, int minStock) throws JRException

// Genera el resumen ejecutivo
byte[] generateSummaryReport() throws JRException
```

Cada método debe:
1. Cargar el `.jrxml` desde el classpath con `JasperCompileManager`
2. Llenar el reporte con `JasperFillManager`
3. Exportar a PDF con `JRPdfExporter`

### 5. Verifica tu implementación

```bash
# Ejecuta todos los tests de integración
mvn test

# Verifica checkstyle
mvn checkstyle:check

# Verifica complejidad ciclomática (debe ser ≤ 10)
mvn pmd:check
```

### 6. Genera los PDFs de muestra

```java
// Puedes usar App.java para generar los PDFs de muestra
mvn exec:java -Dexec.mainClass="com.yura.workshop.App"
```

Los PDFs se generarán en `sample-reports/`. Verifica que tienen contenido visible.

### 7. Abre el Pull Request

```bash
git checkout -b feature/jasper-reports
git add .
git commit -m "feat: implement JasperReports templates and ReportService"
git push origin feature/jasper-reports
```

Abre el PR hacia `main`. El pipeline correrá automáticamente.

**El PR debe incluir:**
- Los 4 archivos `.jrxml` funcionales en `src/main/resources/reports/`
- `ReportService.java` implementado
- Los 3 PDFs de muestra en `sample-reports/`

## Criterios de evaluación

| Métrica | Peso | Umbral |
|---|---|---|
| Cantidad de templates JRXML | 15% | ≥ 3 archivos `.jrxml` |
| Manejo de excepciones | 20% | ≥ 70% de catch con lógica real |
| Tests de integración | 30% | 100% de tests en verde |
| Complejidad ciclomática | 20% | CC ≤ 10 en ReportService |
| Violaciones de Checkstyle | 15% | 0 errores |

## Recursos

- [JasperReports Ultimate Guide](https://community.jaspersoft.com/documentation/)
- [Jaspersoft Studio User Guide](https://community.jaspersoft.com/project/jaspersoft-studio/releases)
- [JRPdfExporter API](https://jasperreports.sourceforge.net/api/net/sf/jasperreports/engine/export/JRPdfExporter.html)
- [Subreportes en JasperReports](https://community.jaspersoft.com/wiki/subreports-jasperreports)
