package co.edu.cue.proyectoNuclearSostenible.infraestructure.dao;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateDao extends JpaRepository<State, Long> {
}
