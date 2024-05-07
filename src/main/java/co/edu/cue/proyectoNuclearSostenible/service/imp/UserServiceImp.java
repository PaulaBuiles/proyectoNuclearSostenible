package co.edu.cue.proyectoNuclearSostenible.service.imp;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.State;
import co.edu.cue.proyectoNuclearSostenible.domain.entities.User;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.UserDao;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.UserDto;
import co.edu.cue.proyectoNuclearSostenible.mapping.mapper.UserMapper;
import co.edu.cue.proyectoNuclearSostenible.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class UserServiceImp implements UserService {

    @Qualifier("userMapper")
    private UserMapper mapper;

    @Autowired
    private UserDao userDao;

    private TypeIdServiceImpl typeIdService;

    public UserDto createUser(UserDto userDto) {
        validateUserInfo(userDto);
        User user = mapper.mapToEntity(userDto);
        user.setTypeIdUser(typeIdService.getById(userDto.typeIdUserId()));
        return mapper.mapToDTO(userDao.save(user));
    }

    private void validateUserInfo(UserDto userDto) {
        String username = userDto.userName().toLowerCase();
        String email = userDto.email().toLowerCase();
        String identification = userDto.identification().toLowerCase();
        String phone = userDto.phone().toLowerCase();

        List<User> existingUsers = userDao.findByUserNameIgnoreCaseOrEmailIgnoreCaseOrIdentificationIgnoreCaseOrPhoneIgnoreCase(username, email, identification, phone);

        if (existingUsers.size() > 1) {
            throw new IllegalArgumentException("Se encontraron múltiples usuarios con las mismas credenciales.");
        }

        if (!existingUsers.isEmpty()) {
            User existingUser = existingUsers.get(0);
            if (existingUser.getUserName().equalsIgnoreCase(username)) {
                throw new IllegalArgumentException("Ya existe un usuario con ese nombre de usuario.");
            } else if (existingUser.getEmail().equalsIgnoreCase(email)) {
                throw new IllegalArgumentException("Ya existe un usuario con ese correo electrónico.");
            } else if (existingUser.getIdentification().equalsIgnoreCase(identification)) {
                throw new IllegalArgumentException("Ya existe un usuario con esa identificación.");
            } else if (existingUser.getPhone().equalsIgnoreCase(phone)) {
                throw new IllegalArgumentException("Ya existe un usuario con ese número de teléfono.");
            }
        }
    }



    public User getUser(UserDto user) {
        return userDao.findById(user.idUser()).get();
    }
    public User getById(Long id){
        return userDao.findUserById(id);
    }
}
