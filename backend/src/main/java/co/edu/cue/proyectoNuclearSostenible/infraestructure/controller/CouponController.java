package co.edu.cue.proyectoNuclearSostenible.infraestructure.controller;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Coupon;
import co.edu.cue.proyectoNuclearSostenible.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupons")
@CrossOrigin(origins = "http://localhost:4200")
public class CouponController {

    @Autowired
    private CouponService couponService;

    /**
     * Permite a un usuario comprar un cupón con sus puntos.
     *
     * @param userId El ID del usuario que desea comprar el cupón.
     * @param couponId El ID del cupón que se desea comprar.
     * @return ResponseEntity con el resultado de la compra.
     */
    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseCoupon(@RequestParam Long userId, @RequestParam Long couponId) {
        try {
            couponService.purchaseCoupon(userId, couponId);
            return new ResponseEntity<>("Cupón comprado con éxito.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Obtiene todos los cupones disponibles.
     *
     * @return ResponseEntity con la lista de todos los cupones y el código de estado HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<?> getAllCoupons() {
        try {
            List<Coupon> coupons = couponService.getAllCoupons();
            return new ResponseEntity<>(coupons, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Obtiene todos los cupones disponibles para el usuario según su id.
     *
     * @param userId El ID del usuario que buscamos.
     * @return ResponseEntity con la lista de todos los cupones y el código de estado HTTP 200 (OK).
     */
    @GetMapping("/user/{userId}")
    public List<Coupon> getCouponsByUserId(@PathVariable Long userId) {
        return couponService.getCouponsByUserId(userId);
    }

}
