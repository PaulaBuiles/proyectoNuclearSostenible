package co.edu.cue.proyectoNuclearSostenible.controller;

import co.edu.cue.proyectoNuclearSostenible.mapping.dto.UserDto;
import co.edu.cue.proyectoNuclearSostenible.model.User;
import co.edu.cue.proyectoNuclearSostenible.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@Controller
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping(headers = "Accept=application/json")
    public ResponseEntity<UserDto> createUser (@RequestBody UserDto user) {
        try {
            //Llamar al servicio
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }



}
