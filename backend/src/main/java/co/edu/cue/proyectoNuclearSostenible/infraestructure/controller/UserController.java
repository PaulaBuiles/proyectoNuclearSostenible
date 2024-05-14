package co.edu.cue.proyectoNuclearSostenible.infraestructure.controller;

import co.edu.cue.proyectoNuclearSostenible.mapping.dto.UserDto;
import co.edu.cue.proyectoNuclearSostenible.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Controller
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * Crea un nuevo usuario en el sistema.
     *
     * @param user El DTO del usuario a crear (en formato JSON en el cuerpo de la solicitud).
     * @return ResponseEntity con el resultado de la creación del usuario.
     *         Si la creación es exitosa, devuelve un ResponseEntity con el cuerpo de la respuesta conteniendo el usuario creado y el código de estado HTTP 200 (OK).
     *         Si ocurre un error durante la creación, devuelve un ResponseEntity con el mensaje de error correspondiente y el código de estado HTTP 409 (Conflict).
     */
    @PostMapping(headers = "Accept=application/json")
    public ResponseEntity<?> createUser (@RequestBody UserDto user) {
        try {
            return new ResponseEntity<>(userService.createUser(user), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    /**
     * Obtiene un usuario del sistema.
     *
     * @param user El DTO del usuario a obtener (en formato JSON en el cuerpo de la solicitud).
     * @return ResponseEntity con el resultado de la obtención del usuario.
     *         Si la obtención es exitosa, devuelve un ResponseEntity con el cuerpo de la respuesta conteniendo el usuario obtenido y el código de estado HTTP 200 (OK).
     *         Si ocurre un error durante la obtención, devuelve un ResponseEntity con el código de estado HTTP 409 (Conflict).
     */
    @GetMapping(headers = "Accept=application/json")
    public ResponseEntity<?> getUser(@RequestBody UserDto user) {
        try {
            return new ResponseEntity<>(userService.getUser(user), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
