package co.edu.cue.proyectoNuclearSostenible.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Entity
@Table(name = "transaction", indexes = @Index(columnList = "idTransaction"))
public class Reward implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAssessment;

    @Column(name = "description")
    private String description;

    @Column(name = "points_value")
    private Integer points_value;
}
