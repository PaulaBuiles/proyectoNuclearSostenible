package co.edu.cue.proyectoNuclearSostenible.infraestructure.dao;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserDao extends JpaRepository<User, Long> {

    List<User> findByUserNameIgnoreCaseOrEmailIgnoreCaseOrIdentificationIgnoreCaseOrPhoneIgnoreCase(String username, String email, String identification, String phone);

    @Query("SELECT t FROM User t WHERE t.idUser = :id")
    User findUserById(@Param("id") Long id);

    User findByUserName(String username);

    Optional<User> findByIdentification(String identification);

    User findByEmail(String email);
}
