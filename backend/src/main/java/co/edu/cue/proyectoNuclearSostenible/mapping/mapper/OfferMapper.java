package co.edu.cue.proyectoNuclearSostenible.mapping.mapper;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Offer;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.OfferDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OfferMapper {

    OfferDto mapToDTO(Offer source);

    Offer mapToEntity(OfferDto source);
}
