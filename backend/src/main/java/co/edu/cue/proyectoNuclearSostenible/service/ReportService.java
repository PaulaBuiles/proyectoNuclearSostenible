package co.edu.cue.proyectoNuclearSostenible.service;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Report;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.ReportDto;

import java.util.List;
import java.util.Optional;

public interface ReportService {

    ReportDto createReport(ReportDto reportDto);
    Optional<Report> findReportById(Long id);

    List<Report> findAllReports();

    Report updateReport(Report report);
}
