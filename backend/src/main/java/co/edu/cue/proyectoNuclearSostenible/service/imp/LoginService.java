package co.edu.cue.proyectoNuclearSostenible.service.imp;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Token;
import co.edu.cue.proyectoNuclearSostenible.domain.entities.User;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.TokenRepository;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.TypeIdDao;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.UserDao;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.AuthenticationResponseDTO;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.StatusDto;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.UserDto;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.UserOutDto;
import co.edu.cue.proyectoNuclearSostenible.mapping.mapper.UserMapper;
import co.edu.cue.proyectoNuclearSostenible.service.UserService;
import co.edu.cue.proyectoNuclearSostenible.domain.enums.CodeMessageEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserDao userDao;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TypeIdDao typeIdDao;

    @Autowired
    private TokenRepository tokenRepository;


    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    /**
     * Crea un nuevo usuario en el sistema.
     *
     * @param userDto DTO del usuario que se desea crear.
     * @return Un DTO de salida que contiene la información del usuario creado, el token de autenticación y el estado de la operación.
     * @throws ResponseStatusException si el usuario ya existe o si no se pudo crear el usuario.
     */
    public UserOutDto createUser(UserDto userDto) {
        log.info("Iniciando crearUser");
        User userToCreate = mapObject(userDto); // Mapea el DTO a la entidad User.
        UserOutDto userOutDto = new UserOutDto();

        // Verifica si el usuario ya existe por su identificación.
        if (userDao.findByIdentification(userToCreate.getIdentification()).isEmpty()) {
            User userNew = userDao.save(userToCreate); // Guarda el nuevo usuario en la base de datos.

            if (userNew != null) {
                String token = jwtService.generateToken(userNew); // Genera un token de autenticación para el nuevo usuario.
                userOutDto.setAuthenticationResponseDto(new AuthenticationResponseDTO(token));
                userOutDto.setUser(userNew);
                userOutDto.setStatusDto(new StatusDto(CodeMessageEnum.SUCCESSFUL.getCode(), CodeMessageEnum.SUCCESSFUL.getMessage()));
                log.info("Finalizando crearUser");
                return userOutDto;
            } else {
                userOutDto.setStatusDto(new StatusDto(CodeMessageEnum.ERROR_INVALID_RESULT.getCode(), CodeMessageEnum.ERROR_INVALID_RESULT.getMessage()));
                log.error(CodeMessageEnum.ERROR_INVALID_RESULT.getMessage() + " crearUser");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User no encontrado");
            }
        }
        userOutDto.setStatusDto(new StatusDto(CodeMessageEnum.ERROR_INVALID_RESULT.getCode(), "User con número de identificación repetido"));
        log.error(CodeMessageEnum.ERROR_INVALID_RESULT.getMessage() + " crearUser");
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User con número de identificación repetido");
    }


    /**
     * Mapea un objeto UserDto a una entidad User y realiza configuraciones adicionales.
     *
     * @param userDto DTO del usuario que se desea mapear.
     * @return La entidad User mapeada y configurada.
     * @throws ResponseStatusException si el tipo de identificación no se encuentra.
     */
    private User mapObject(UserDto userDto) {
        User user = userMapper.mapToEntity(userDto); // Mapea el DTO a la entidad User.
        user.setIsAdmin(userDto.isAdmin()); // Configura si el usuario es administrador.
        user.setPassword(passwordEncoder.encode(userDto.password())); // Codifica y configura la contraseña del usuario.
        user.setTypeIdUser(typeIdDao.findById(userDto.typeIdUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo de identificación no encontrado"))); // Busca y configura el tipo de identificación del usuario.
        return user;
    }


    /**
     * Autentica un usuario basado en su nombre de usuario y contraseña.
     *
     * @param userDto DTO del usuario que contiene las credenciales de autenticación.
     * @return UserOutDto que contiene la información del usuario y el token de autenticación.
     * @throws ResponseStatusException si el usuario no es encontrado o las credenciales son inválidas.
     */
    public UserOutDto authenticate(UserDto userDto) {
        log.info("Iniciando autenticar");

        UserOutDto userOutDto = new UserOutDto();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.userName(), userDto.password()));
        } catch (AuthenticationException e) {
            userOutDto.setStatusDto(new StatusDto(CodeMessageEnum.ERROR_INVALID_RESULT.getCode(), "Credenciales inválidas"));
            log.error("Credenciales inválidas: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
        }

        User user = userDao.findByUserName(userDto.userName());

        if (user != null) {
            if (Boolean.FALSE.equals(user.getStatus())) {
                userOutDto.setStatusDto(new StatusDto(CodeMessageEnum.ERROR_INVALID_RESULT.getCode(), "Tu usuario ha sido desactivado por alta cantidad de reportes"));
                log.error("Usuario desactivado por alta cantidad de reportes");
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Tu usuario ha sido desactivado por alta cantidad de reportes");
            }

            String jwt = jwtService.generateToken(user);

            deleteUserToken(user);
            saveUserToken(jwt, user);

            userOutDto.setAuthenticationResponseDto(new AuthenticationResponseDTO(jwt));
            userOutDto.setUser(user);
            userOutDto.setStatusDto(new StatusDto(CodeMessageEnum.SUCCESSFUL.getCode(), CodeMessageEnum.SUCCESSFUL.getMessage()));

            log.info("Finalizando autenticar");
            return userOutDto;
        } else {
            userOutDto.setStatusDto(new StatusDto(CodeMessageEnum.ERROR_INVALID_RESULT.getCode(), "Usuario no encontrado"));
            log.error(CodeMessageEnum.ERROR_INVALID_RESULT.getMessage() + " autenticar");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario no encontrado");
        }
    }



    /**
     * Invalida los tokens activos de un usuario, marcándolos como cerrados.
     *
     * @param user El usuario cuyos tokens deben ser invalidados.
     */
    private void deleteUserToken(User user) {
        List<Token> lstValidToken = tokenRepository.findByUserAndIsLogOut(user, false);

        if (!lstValidToken.isEmpty()) {
            // Marcar cada token como cerrado
            lstValidToken.forEach(t -> t.setIsLogOut(true));
        }

        tokenRepository.saveAll(lstValidToken);
    }


    /**
     * Guarda un nuevo token JWT para un usuario específico.
     *
     * @param jwt  El token JWT a guardar.
     * @param user El usuario al que se asignará el token.
     */
    private void saveUserToken(String jwt, User user) {
        Token token = new Token();
        token.setToken(jwt);
        token.setUser(user);
        token.setIsLogOut(false);
        tokenRepository.save(token);
    }


}
