package co.edu.cue.proyectoNuclearSostenible.service;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Reward;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.RewardDto;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.UserDto;

import java.util.List;

public interface RewardService {

    void addPoints(UserDto userDto, int points, String description);

    void redeemPoints(UserDto userDto, int points);

    List<Reward> getRewardsByUserId(Long userId);

    Integer getTotalPointsByUserId(Long userId);

}
