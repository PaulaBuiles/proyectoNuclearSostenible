package co.edu.cue.proyectoNuclearSostenible.infraestructure.dao;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Token;
import co.edu.cue.proyectoNuclearSostenible.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    List<Token> findByUserAndIsLogOut(User user, Boolean isLogged);
    Optional<Token> findByToken(String token);
}