package co.edu.cue.proyectoNuclearSostenible.mapping.mapper;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Report;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.ReportDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface ReportMapper {
    ReportDto mapToDTO(Report source);
    Report mapToEntity(ReportDto source);
}
