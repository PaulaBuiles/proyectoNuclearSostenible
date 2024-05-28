package co.edu.cue.proyectoNuclearSostenible.service;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.User;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.UserDto;


public interface UserService {

    User getUser(UserDto user);

    User getById(Long id);

    User getUserById(Long id);

    int getPoints(UserDto userDto);

    UserDto editUser(Long userId, UserDto userDto);
}
