package co.edu.cue.proyectoNuclearSostenible.service;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.TypeId;

public interface TypeIdService {
    TypeId createType(TypeId typeId);

    TypeId validateInfo(TypeId typeId);
}
