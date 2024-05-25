package co.edu.cue.proyectoNuclearSostenible.infraestructure.controller;

import co.edu.cue.proyectoNuclearSostenible.mapping.dto.UserDto;
import co.edu.cue.proyectoNuclearSostenible.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rewards")
@CrossOrigin(origins = "http://localhost:4200")
public class RewardController {
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
    public void addPoints(@RequestBody UserDto userDto, @RequestParam int points, @RequestParam String description) {
        rewardService.addPoints(userDto, points, description);
    }

    /**
     * Endpoint para canjear puntos de un usuario.
     *
     * @param userDto DTO del usuario.
     * @param points Puntos a canjear.
     */
    @PostMapping("/redeemPoints")
    public void redeemPoints(@RequestBody UserDto userDto, @RequestParam int points) {
        rewardService.redeemPoints(userDto, points);
    }

    /**
     * Endpoint para obtener los puntos de un usuario.
     *
     * @param userDto DTO del usuario.
     * @return Cantidad de puntos del usuario.
     */
    @PostMapping("/getPoints")
    public int getPoints(@RequestBody UserDto userDto) {
        return rewardService.getPoints(userDto);
    }
}
