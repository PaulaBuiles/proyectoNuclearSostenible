package co.edu.cue.proyectoNuclearSostenible.infraestructure.dao;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponDao extends JpaRepository<Coupon,Long> {



}
