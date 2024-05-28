package co.edu.cue.proyectoNuclearSostenible.service.imp;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.TypeId;
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
import java.util.NoSuchElementException;


@Service
@AllArgsConstructor
public class UserServiceImp implements UserService, UserDetailsService {

    @Qualifier("userMapper")
    private UserMapper mapper;

    @Autowired
    private UserDao userDao;

    private TypeIdServiceImpl typeIdService;


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

    /**
     * Obtiene los puntos de un usuario.
     *
     * @param userDto DTO del usuario.
     * @return La cantidad de puntos del usuario.
     */
    @Override
    public int getPoints(UserDto userDto) {
        User user = mapper.mapToEntity(userDto);
        return user.getPoints();
    }

    /**
     * Edita un producto existente en el sistema.
     *
     * @param userId El ID del producto a editar.
     * @param userDto Los datos actualizados del producto.
     * @return El DTO del producto editado.
     * @throws NoSuchElementException Si el producto con el ID proporcionado no se encuentra.
     */
    public UserDto editUser(Long userId, UserDto userDto) {
        User existingUser = userDao.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado con el ID " + userId));

        existingUser.setUserName(userDto.userName());
        existingUser.setPassword(userDto.password());
        existingUser.setFullName(userDto.fullName());
        existingUser.setEmail(userDto.email());
        existingUser.setPhone(userDto.phone());

        TypeId typeId = typeIdService.getById(userDto.typeIdUserId());
        existingUser.setTypeIdUser(typeId);

        existingUser.setIdentification(userDto.identification());
        existingUser.setImage(userDto.image());
        existingUser.setDescription(userDto.description());
        existingUser.setStatus(userDto.status());
        existingUser.setPoints(userDto.points());
        existingUser.setIsAdmin(userDto.isAdmin());

        return mapper.mapToDTO(userDao.save(existingUser));
    }


}
