package co.edu.cue.proyectoNuclearSostenible.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Entity
@Table(name = "report", indexes = @Index(columnList = "idReport"))
public class Report implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReport;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name="id_denounced")
    private User denounced;

    @ManyToOne
    @JoinColumn(name="id_complainant")
    private User complainant;
    
}
