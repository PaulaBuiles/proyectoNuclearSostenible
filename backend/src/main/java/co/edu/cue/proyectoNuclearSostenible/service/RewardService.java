package co.edu.cue.proyectoNuclearSostenible.service;

import co.edu.cue.proyectoNuclearSostenible.mapping.dto.RewardDto;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.UserDto;

public interface RewardService {

    void addPoints(UserDto userDto, int points, String description);

    void redeemPoints(UserDto userDto, int points);

    int getPoints(UserDto userDto);
}
