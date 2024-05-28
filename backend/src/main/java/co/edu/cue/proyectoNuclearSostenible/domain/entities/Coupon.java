package co.edu.cue.proyectoNuclearSostenible.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "coupons", indexes = @Index(columnList = "idCoupon"))
public class Coupon implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCoupon;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "pointsValue", nullable = false)
    private int pointsValue;

    @ManyToMany(mappedBy = "coupons")
    private List<User> users;

}
