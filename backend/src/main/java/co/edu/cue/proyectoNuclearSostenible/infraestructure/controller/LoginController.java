package co.edu.cue.proyectoNuclearSostenible.infraestructure.controller;

import co.edu.cue.proyectoNuclearSostenible.mapping.dto.UserDto;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.UserOutDto;
import co.edu.cue.proyectoNuclearSostenible.service.imp.LoginService;
import co.edu.cue.proyectoNuclearSostenible.domain.enums.CodeMessageEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * Endpoint para registrar un nuevo usuario.
     *
     * @param userDto DTO del usuario a registrar.
     * @return ResponseEntity con el DTO de salida del usuario registrado y el estado HTTP correspondiente.
     */
    @PostMapping("/createUser")
    public ResponseEntity<UserOutDto> register(@RequestBody UserDto userDto) {
        try {
            UserOutDto userOutDto = loginService.createUser(userDto);
            if (CodeMessageEnum.SUCCESSFUL.getCode().equals(userOutDto.getStatusDto().getCode())) {
                return ResponseEntity.ok(userOutDto);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userOutDto);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    /**
     * Endpoint para autenticar a un usuario.
     *
     * @param userDto DTO del usuario a autenticar.
     * @return ResponseEntity con el DTO de salida del usuario autenticado y el estado HTTP correspondiente.
     */
    /**
     * Endpoint para autenticar a un usuario.
     *
     * @param userDto DTO del usuario a autenticar.
     * @return ResponseEntity con el DTO de salida del usuario autenticado y el estado HTTP correspondiente.
     */
    @PostMapping("/authenticate")
    public ResponseEntity<UserOutDto> login(@RequestBody UserDto userDto) {
        try {
            UserOutDto userOutDto = loginService.authenticate(userDto);
            if (CodeMessageEnum.SUCCESSFUL.getCode().equals(userOutDto.getStatusDto().getCode())) {
                return ResponseEntity.ok(userOutDto);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userOutDto);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



}
