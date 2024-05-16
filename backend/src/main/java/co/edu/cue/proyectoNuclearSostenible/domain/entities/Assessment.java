package co.edu.cue.proyectoNuclearSostenible.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Entity
@Table(name = "assessment", indexes = @Index(columnList = "idAssessment"))
public class Assessment implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAssessment;

    @Column(name = "comment")
    private String comment;

    @Column(name = "rating", nullable = false)
    private String rating;

    @OneToOne
    @JoinColumn(name="id_transaction")
    private Transaction id_transaction;



}
