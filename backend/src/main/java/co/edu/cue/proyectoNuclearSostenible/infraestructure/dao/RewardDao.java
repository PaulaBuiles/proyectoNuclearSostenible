package co.edu.cue.proyectoNuclearSostenible.infraestructure.dao;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Reward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RewardDao extends JpaRepository<Reward, Long> {

    @Query("SELECT r FROM Reward r JOIN r.users u WHERE u.idUser = :userId")
    List<Reward> findRewardsByUserId(@Param("userId") Long userId);

    @Query("SELECT SUM(r.points_value) FROM Reward r JOIN r.users u WHERE u.idUser = :userId")
    Integer findTotalPointsByUserId(@Param("userId") Long userId);
}
