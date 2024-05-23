package co.edu.cue.proyectoNuclearSostenible.service;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Report;
import java.util.List;
import java.util.Optional;

public interface ReportService {

    Report createReport(Report report);
    Optional<Report> findReportById(Long id);

    List<Report> findAllReports();

    Report updateReport(Report report);
}
