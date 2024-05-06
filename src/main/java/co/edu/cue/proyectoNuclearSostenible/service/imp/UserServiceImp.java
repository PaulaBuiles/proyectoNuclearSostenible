package co.edu.cue.proyectoNuclearSostenible.service.imp;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.User;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.UserDao;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.UserDto;
import co.edu.cue.proyectoNuclearSostenible.mapping.mapper.UserMapper;
import co.edu.cue.proyectoNuclearSostenible.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserServiceImp implements UserService {

    @Qualifier("userMapper")
    private UserMapper mapper;

    @Autowired
    private UserDao userDao;

    private TypeIdServiceImpl typeIdService;

    public UserDto createUser(UserDto userDto) {
        User user = mapper.mapToEntity(userDto);
        user.setTypeIdUser(typeIdService.getById(userDto.typeIdUserId()));
        return mapper.mapToDTO(userDao.save(user));
    }

    public User getUser(UserDto user) {
        return userDao.findById(user.idUser()).get();
    }
}
