package co.edu.cue.proyectoNuclearSostenible.mapping.mapper;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Assessment;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.AssessmentDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface AssessmentMapper {
    AssessmentDto mapToDTO(Assessment source);
    Assessment mapToEntity(AssessmentDto source);
}
