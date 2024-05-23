package co.edu.cue.proyectoNuclearSostenible.service.imp;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.User;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.UserDao;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.UserDto;
import co.edu.cue.proyectoNuclearSostenible.mapping.mapper.UserMapper;
import co.edu.cue.proyectoNuclearSostenible.service.UserService;
import co.edu.cue.proyectoNuclearSostenible.config.Validation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class UserServiceImp implements UserService, UserDetailsService {

    @Qualifier("userMapper")
    private UserMapper mapper;

    @Autowired
    private UserDao userDao;

    private TypeIdServiceImpl typeIdService;

    /**
     * Crea un nuevo usuario en el sistema.
     *
     * @param userDto Los datos del usuario a crear.
     * @return El DTO del usuario creado.
     * @throws IllegalArgumentException Si se encuentran usuarios con las mismas credenciales o si ya existe un usuario con alguno de los datos proporcionados.
     */
    public UserDto createUser(UserDto userDto) {
        // Validar la información del usuario
        validateUserInfo(userDto);

        // Mapear DTO a entidad
        User user = mapper.mapToEntity(userDto);

        // Obtener y configurar el tipo de identificación del usuario
        user.setTypeIdUser(typeIdService.getById(userDto.typeIdUserId()));

        // Guardar el usuario en la base de datos y mapear el resultado a un DTO
        return mapper.mapToDTO(userDao.save(user));
    }

    /**
     * Valida la información del usuario antes de crearlo.
     *
     * @param userDto Los datos del usuario a validar.
     * @throws IllegalArgumentException Si se encuentran múltiples usuarios con las mismas credenciales o si ya existe un usuario con alguno de los datos proporcionados.
     */
    private void validateUserInfo(UserDto userDto) {

        String username = userDto.userName().toLowerCase();
        String email = userDto.email().toLowerCase();
        String identification = userDto.identification().toLowerCase();
        String phone = userDto.phone().toLowerCase();

        // Buscar usuarios existentes con las mismas credenciales
        List<User> existingUsers = userDao.findByUserNameIgnoreCaseOrEmailIgnoreCaseOrIdentificationIgnoreCaseOrPhoneIgnoreCase(username, email, identification, phone);

        // Verificar si se encontraron múltiples usuarios con las mismas credenciales
        if (existingUsers.size() > 1) {
            throw new IllegalArgumentException("Se encontraron múltiples usuarios con las mismas credenciales.");
        }

        // Verificar si ya existe un usuario con alguno de los datos proporcionados
        if (!existingUsers.isEmpty()) {
            User existingUser = existingUsers.get(0);
            if (existingUser.getUsername().equalsIgnoreCase(username)) {
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

    /**
     * Obtiene un usuario a partir de su DTO.
     *
     * @param user El DTO del usuario.
     * @return El usuario correspondiente al DTO proporcionado.
     */
    public User getUser(UserDto user) {
        return userDao.findById(user.idUser()).get();
    }

    public User getUserById(Long id) {
        return userDao.findById(id).get();
    }

    /**
     * Obtiene un usuario a partir de su ID.
     *
     * @param id El ID del usuario a obtener.
     * @return El usuario correspondiente al ID proporcionado.
     */
    public User getById(Long id){
        return userDao.findUserById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = userDao.findByUserName(username);
        if (!Validation.isNullOrEmpty(user)) {
        return org.springframework.security.core.userdetails.User.builder().username(user.getUsername()).password(user.getPassword()).roles(user.getRol()).build();

        } else {
            throw new UsernameNotFoundException(username);
        }
    }



}
