package co.edu.cue.proyectoNuclearSostenible.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "reward", indexes = @Index(columnList = "idReward"))
public class Reward implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReward;

    @Column(name = "description")
    private String description;

    @Column(name = "points_value")
    private Integer points_value;

    @ManyToMany(mappedBy = "rewards")
    private Set<User> userRewards = new HashSet<>();
}
