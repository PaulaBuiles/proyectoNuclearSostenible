package co.edu.cue.proyectoNuclearSostenible.infraestructure.controller;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Report;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.PublicationDto;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.ReportDto;
import co.edu.cue.proyectoNuclearSostenible.service.ReportService;
import co.edu.cue.proyectoNuclearSostenible.service.imp.ReportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "http://localhost:4200")
public class ReportController {

    @Autowired
    private ReportService reportService;

    /**
     * Crea un nuevo reporte.
     *
     * @param reportDto Datos del reporte a crear.
     * @return ResponseEntity con el reporte creado.
     */
    @PostMapping(headers = "Accept=application/json")
    public ResponseEntity<?> createReport (@RequestBody ReportDto reportDto) {
        try {
            return new ResponseEntity<>(reportService.createReport(reportDto), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }


    /**
     * Obtiene un reporte por su ID.
     *
     * @param id ID del reporte a obtener.
     * @return ResponseEntity con el reporte encontrado o not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getReportById(@PathVariable Long id) {
        try {
            Optional<Report> report = reportService.findReportById(id);
            return report.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    /**
     * Obtiene todos los reportes.
     *
     * @return ResponseEntity con la lista de todos los reportes.
     */
    @GetMapping
    public ResponseEntity<?> getAllReports() {
        try {
            List<Report> reports = reportService.findAllReports();
            return ResponseEntity.ok(reports);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    /**
     * Actualiza un reporte existente.
     *
     * @param id ID del reporte a actualizar.
     * @param updatedReport Datos actualizados del reporte.
     * @return ResponseEntity con el reporte actualizado o not found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateReport(@PathVariable Long id, @RequestBody Report updatedReport) {
        try {
            Optional<Report> report = reportService.findReportById(id);
            if (report.isPresent()) {
                updatedReport.setIdReport(id);
                Report savedReport = reportService.updateReport(updatedReport);
                return ResponseEntity.ok(savedReport);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/list-user/{id}")
    public ResponseEntity<?> getComplainantByUserId(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(reportService.getReportByUserId(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
}
