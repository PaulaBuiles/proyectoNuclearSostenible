package co.edu.cue.proyectoNuclearSostenible.infraestructure.controller;

import co.edu.cue.proyectoNuclearSostenible.mapping.dto.UserDto;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.UserOutDto;
import co.edu.cue.proyectoNuclearSostenible.service.imp.LoginService;
import co.edu.cue.proyectoNuclearSostenible.domain.enums.CodeMessageEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/persona")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/crearUsuario")
    public ResponseEntity<UserOutDto> register(@RequestBody UserDto userDto) {
        UserOutDto userOutDto = loginService.createUser(userDto);
        if (CodeMessageEnum.SUCCESSFUL.getCode().equals(userOutDto.getStatusDto().getCode())) {
            return ResponseEntity.ok(userOutDto);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userOutDto);
        }
    }

    @PostMapping("/autenticar")
    public ResponseEntity<UserOutDto> login(@RequestBody UserDto userDto) {
        UserOutDto userOutDto = loginService.autenticar(userDto);
        if (CodeMessageEnum.SUCCESSFUL.getCode().equals(userOutDto.getStatusDto().getCode())) {
            return ResponseEntity.ok(userOutDto);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userOutDto);
        }
    }

}
