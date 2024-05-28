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
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * Obtiene un usuario del sistema.
     *
     * @return ResponseEntity con el resultado de la obtención del usuario.
     *         Si la obtención es exitosa, devuelve un ResponseEntity con el cuerpo de la respuesta conteniendo el usuario obtenido y el código de estado HTTP 200 (OK).
     *         Si ocurre un error durante la obtención, devuelve un ResponseEntity con el código de estado HTTP 409 (Conflict).
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    /**
     * Endpoint para obtener los puntos de un usuario.
     *
     * @param userDto DTO del usuario.
     * @return Cantidad de puntos del usuario.
     */
    @PostMapping("/getPoints")
    public int getPoints(@RequestBody UserDto userDto) {
        return userService.getPoints(userDto);
    }


    /**
     * Edita un usuario existente en el sistema.
     *
     * @param id El ID del usuario a editar.
     * @param userDTO Los datos actualizados del usuario.
     * @return ResponseEntity con el usuario editado y el código de estado HTTP 200 (OK).
     *         Si ocurre un error, devuelve un ResponseEntity con el mensaje de error correspondiente y el código de estado HTTP 404 (Not Found).
     */
    @PutMapping("/{id}")
    public UserDto editUser(@PathVariable Long id, @RequestBody UserDto userDTO) {
        return userService.editUser(id, userDTO);
    }

}
