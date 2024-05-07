package co.edu.cue.proyectoNuclearSostenible.infraestructure.dao;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StateDao extends JpaRepository<State, Long> {
    State findByDescriptionIgnoreCase(String lowercaseDescription);

    @Query("SELECT t FROM State t WHERE t.idState = :id")
    State findStateById(@Param("id") Long id);
}
