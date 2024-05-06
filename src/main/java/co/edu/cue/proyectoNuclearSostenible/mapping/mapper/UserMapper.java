package co.edu.cue.proyectoNuclearSostenible.mapping.mapper;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.User;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.UserDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface UserMapper {

    UserDto mapToDTO(User source);

    User mapToEntity(UserDto source);
}
