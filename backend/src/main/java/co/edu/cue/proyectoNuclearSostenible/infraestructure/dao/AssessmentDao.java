package co.edu.cue.proyectoNuclearSostenible.infraestructure.dao;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Assessment;
import co.edu.cue.proyectoNuclearSostenible.domain.entities.Offer;
import co.edu.cue.proyectoNuclearSostenible.domain.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssessmentDao extends JpaRepository<Assessment,Long> {
}
