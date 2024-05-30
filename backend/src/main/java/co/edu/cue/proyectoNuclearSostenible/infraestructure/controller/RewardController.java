package co.edu.cue.proyectoNuclearSostenible.infraestructure.controller;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Reward;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.UserDto;
import co.edu.cue.proyectoNuclearSostenible.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rewards")
@CrossOrigin(origins = "http://localhost:4200")
public class RewardController {

    @Autowired
    private final RewardService rewardService;

    @Autowired
    public RewardController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    /**
     * Endpoint para agregar puntos a un usuario.
     *
     * @param userDto DTO del usuario.
     * @param points Puntos a agregar.
     * @param description Descripción de la recompensa.
     */
    @PostMapping("/addPoints")
    public ResponseEntity<?> addPoints(@RequestBody UserDto userDto, @RequestParam int points, @RequestParam String description) {
        try {
            rewardService.addPoints(userDto, points, description);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    /**
     * Endpoint para canjear puntos de un usuario.
     *
     * @param userDto DTO del usuario.
     * @param points Puntos a canjear.
     */
    @PostMapping("/redeemPoints")
    public ResponseEntity<?> redeemPoints(@RequestBody UserDto userDto, @RequestParam int points) {
        try {
            rewardService.redeemPoints(userDto, points);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    /**
     * Obtiene todas las recompensas asociadas a un usuario específico.
     *
     * Este método maneja las solicitudes GET para obtener una lista de recompensas
     * asociadas a un usuario identificado por su ID.
     *
     * @param userId El ID del usuario cuyas recompensas se desean obtener.
     * @return Una lista de recompensas asociadas al usuario.
     */
    @GetMapping("/user/{userId}")
    public List<Reward> getRewardsByUserId(@PathVariable Long userId) {
        return rewardService.getRewardsByUserId(userId);
    }


    /**
     * Obtiene el total de puntos acumulados por un usuario específico.
     *
     * Este método maneja las solicitudes GET para obtener el total de puntos
     * acumulados por un usuario identificado por su ID.
     *
     * @param userId El ID del usuario cuyos puntos totales se desean obtener.
     * @return El total de puntos acumulados por el usuario.
     */
    @GetMapping("/user/{userId}/total-points")
    public Integer getTotalPointsByUserId(@PathVariable Long userId) {
        return rewardService.getTotalPointsByUserId(userId);
    }

}
