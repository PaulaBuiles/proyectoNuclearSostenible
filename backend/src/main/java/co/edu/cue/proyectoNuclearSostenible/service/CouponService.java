package co.edu.cue.proyectoNuclearSostenible.service;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Coupon;

import java.util.List;

public interface CouponService {

    Coupon purchaseCoupon(Long userId, Long couponId);

    List<Coupon> getAllCoupons();

    List<Coupon> getCouponsByUserId(Long userId);

}
