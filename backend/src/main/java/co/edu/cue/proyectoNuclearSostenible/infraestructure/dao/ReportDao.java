package co.edu.cue.proyectoNuclearSostenible.infraestructure.dao;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportDao extends JpaRepository<Report, Long> {

    List<Report> findByComplainant_IdUserOrDenounced_IdUser(Long complainantId, Long denouncedId);
}
