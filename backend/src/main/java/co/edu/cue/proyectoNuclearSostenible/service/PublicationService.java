package co.edu.cue.proyectoNuclearSostenible.service;


import co.edu.cue.proyectoNuclearSostenible.domain.entities.Publication;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.PublicationDto;

import java.util.List;


public interface PublicationService {

    PublicationDto createPublication(PublicationDto publicationDto);

    Publication getPublication(PublicationDto publicationDto);

    List<Publication> searchPublications(
            String title,
            String productName,
            String productDescription,
            String categoryTitle,
            String stateDescription);

    List<Publication> getPublicationByUserId(Long id);

    List<Publication> getPublicationByProductId(Long id);
}
