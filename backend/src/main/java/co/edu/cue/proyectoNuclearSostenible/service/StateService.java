package co.edu.cue.proyectoNuclearSostenible.service;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.State;

public interface StateService {
    State createState(State state);

    State getById(Long id);
}
