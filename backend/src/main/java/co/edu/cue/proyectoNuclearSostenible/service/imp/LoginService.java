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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

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

    public UserOutDto createUser(UserDto userDto) {
        log.info("Iniciando crearUser");
        User userToCreate = mapearObjeto(userDto);
        UserOutDto userOutDto = new UserOutDto();

        if (userDao.findByIdentification(userToCreate.getIdentification()).isEmpty()) {
            User userNew = userDao.save(userToCreate);
            if (userNew != null) {
                String token = jwtService.generateToken(userNew);
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

    private User mapearObjeto(UserDto userDto) {
        UserMapper userMapper = new UserMapperImpl();
        User user = userMapper.mapToEntity(userDto);
        user.setIsAdmin(userDto.isAdmin());
        user.setPassword(passwordEncoder.encode(userDto.password()));
        user.setTypeIdUser(typeIdDao.findById(userDto.typeIdUserId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo de identificación no encontrado")));
        return user;
    }

    public UserOutDto autenticar(UserDto userDto) {
        log.info("Iniciando autenticar");
        UserOutDto userOutDto = new UserOutDto();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.userName(), userDto.password()));
        User user = userDao.findByUserName(userDto.userName());
        if (user != null) {
            String jwt = jwtService.generateToken(user);
            deleteUserToken(user);
            saveUserToken(jwt, user);

            userOutDto.setAuthenticationResponseDto(new AuthenticationResponseDTO(jwt));
            userOutDto.setUser(user);
            userOutDto.setStatusDto(new StatusDto(CodeMessageEnum.SUCCESSFUL.getCode(), CodeMessageEnum.SUCCESSFUL.getMessage()));
            log.info("Finalizando autenticar");
            return userOutDto;
        } else {
            userOutDto.setStatusDto(new StatusDto(CodeMessageEnum.ERROR_INVALID_RESULT.getCode(), "User no encontrado"));
            log.error(CodeMessageEnum.ERROR_INVALID_RESULT.getMessage() + " autenticar");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User no encontrado");
        }
    }

    private void deleteUserToken(User user){
        List<Token> lstValidToken = tokenRepository.findByUserAndIsLogOut(user, false);

        if (!lstValidToken.isEmpty()) {
            lstValidToken.forEach(t -> {
                t.setIsLogOut(true);
            });
        }
        tokenRepository.saveAll(lstValidToken);
    }

    private void saveUserToken(String jwt, User user){
        Token token = new Token();
        token.setToken(jwt);
        token.setUser(user);
        token.setIsLogOut(false);
        tokenRepository.save(token);
    }

}
