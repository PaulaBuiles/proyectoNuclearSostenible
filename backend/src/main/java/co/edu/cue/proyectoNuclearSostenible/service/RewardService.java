package co.edu.cue.proyectoNuclearSostenible.service;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.User;

public interface RewardService {

    void addPoints(User user, int points, String description);
    void redeemPoints(User user, int points, String rewardDescription);
    int getPoints(User user);

}
