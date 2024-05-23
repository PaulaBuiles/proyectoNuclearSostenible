package co.edu.cue.proyectoNuclearSostenible.config;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.User;

public class Validation {


    public static boolean isNullOrEmpty(User user) {
        return user == null || user.getUsername().isEmpty() || user.getPassword().isEmpty();
    }
}
