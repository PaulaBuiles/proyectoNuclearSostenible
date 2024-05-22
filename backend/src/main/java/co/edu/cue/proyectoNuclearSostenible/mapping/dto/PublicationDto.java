package co.edu.cue.proyectoNuclearSostenible.mapping.dto;

import java.util.Date;

public record PublicationDto(Long idPublication,
                             Long ownerId,
                             Long productId,
                             String title,
                             Date dateCreated,
                             Long stateId) {
}
