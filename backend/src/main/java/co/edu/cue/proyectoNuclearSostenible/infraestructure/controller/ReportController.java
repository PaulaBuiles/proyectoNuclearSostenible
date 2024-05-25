package co.edu.cue.proyectoNuclearSostenible.infraestructure.controller;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Report;
import co.edu.cue.proyectoNuclearSostenible.service.imp.ReportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "http://localhost:4200")
public class ReportController {

    @Autowired
    private ReportServiceImpl reportService;

    /**
     * Crea un nuevo reporte.
     *
     * @param report Datos del reporte a crear.
     * @return ResponseEntity con el reporte creado.
     */
    @PostMapping
    public ResponseEntity<Report> createReport(@RequestBody Report report) {
        Report createdReport = reportService.createReport(report);
        return ResponseEntity.ok(createdReport);
    }

    /**
     * Obtiene un reporte por su ID.
     *
     * @param id ID del reporte a obtener.
     * @return ResponseEntity con el reporte encontrado o not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable Long id) {
        Optional<Report> report = reportService.findReportById(id);
        return report.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Obtiene todos los reportes.
     *
     * @return ResponseEntity con la lista de todos los reportes.
     */
    @GetMapping
    public ResponseEntity<List<Report>> getAllReports() {
        List<Report> reports = reportService.findAllReports();
        return ResponseEntity.ok(reports);
    }

    /**
     * Actualiza un reporte existente.
     *
     * @param id ID del reporte a actualizar.
     * @param updatedReport Datos actualizados del reporte.
     * @return ResponseEntity con el reporte actualizado o not found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Report> updateReport(@PathVariable Long id, @RequestBody Report updatedReport) {
        Optional<Report> report = reportService.findReportById(id);
        if (report.isPresent()) {
            updatedReport.setIdReport(id);
            Report savedReport = reportService.updateReport(updatedReport);
            return ResponseEntity.ok(savedReport);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
