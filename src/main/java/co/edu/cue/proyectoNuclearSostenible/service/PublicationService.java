package co.edu.cue.proyectoNuclearSostenible.service;


import co.edu.cue.proyectoNuclearSostenible.domain.entities.Publication;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.PublicationDto;


public interface PublicationService {

    PublicationDto createPublication(PublicationDto publicationDto);

    Publication getPublication(PublicationDto publicationDto);

}
