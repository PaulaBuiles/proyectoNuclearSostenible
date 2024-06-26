package co.edu.cue.proyectoNuclearSostenible.infraestructure.dao;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CouponDao extends JpaRepository<Coupon,Long> {

    @Query("SELECT c FROM Coupon c JOIN c.users u WHERE u.idUser = :userId")
    List<Coupon> findCouponsByUserId(Long userId);


}
