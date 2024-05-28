package co.edu.cue.proyectoNuclearSostenible.service.imp;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Coupon;
import co.edu.cue.proyectoNuclearSostenible.domain.entities.User;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.CouponDao;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.UserDao;
import co.edu.cue.proyectoNuclearSostenible.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.NoSuchElementException;

public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponDao couponDao;

    @Autowired
    private UserDao userDao;

    /**
     * Permite a un usuario comprar un cupón con sus puntos.
     *
     * @param userId El ID del usuario que desea comprar el cupón.
     * @param couponId El ID del cupón que se desea comprar.
     * @throws NoSuchElementException Si el usuario o el cupón no se encuentran.
     * @throws IllegalArgumentException Si el usuario no tiene suficientes puntos.
     */
    public void purchaseCoupon(Long userId, Long couponId) {
        User user = userDao.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado con el ID " + userId));
        Coupon coupon = couponDao.findById(couponId)
                .orElseThrow(() -> new NoSuchElementException("Cupón no encontrado con el ID " + couponId));

        if (user.getPoints() < coupon.getPointsValue()) {
            throw new IllegalArgumentException("El usuario no tiene suficientes puntos para comprar este cupón.");
        }

        user.setPoints(user.getPoints() - coupon.getPointsValue());
        user.getCoupons().add(coupon);
        userDao.save(user);
    }

    /**
     * Obtiene todos los cupones disponibles.
     *
     * @return Lista de todos los cupones.
     */
    public List<Coupon> getAllCoupons() {
        return couponDao.findAll();
    }

}
