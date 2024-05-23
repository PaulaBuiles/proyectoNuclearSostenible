package co.edu.cue.proyectoNuclearSostenible.infraestructure.dao;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportDao extends JpaRepository<Report, Long> {

}
