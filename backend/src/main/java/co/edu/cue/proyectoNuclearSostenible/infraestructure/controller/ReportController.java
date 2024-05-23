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
public class ReportController {

    @Autowired
    private ReportServiceImpl reportService;

    @PostMapping
    public ResponseEntity<Report> createReport(@RequestBody Report report) {
        Report createdReport = reportService.createReport(report);
        return ResponseEntity.ok(createdReport);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable Long id) {
        Optional<Report> report = reportService.findReportById(id);
        return report.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Report>> getAllReports() {
        List<Report> reports = reportService.findAllReports();
        return ResponseEntity.ok(reports);
    }

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
