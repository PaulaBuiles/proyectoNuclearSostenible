package co.edu.cue.proyectoNuclearSostenible.service;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Coupon;

import java.util.List;

public interface CouponService {

    void purchaseCoupon(Long userId, Long couponId);

    List<Coupon> getAllCoupons();

}
