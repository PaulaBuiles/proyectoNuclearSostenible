package co.edu.cue.proyectoNuclearSostenible.service;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.User;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.UserDto;


public interface UserService {
    UserDto createUser(UserDto user);

    User getUser(UserDto user);

    User getById(Long id);

    User getUserById(Long id);

}
