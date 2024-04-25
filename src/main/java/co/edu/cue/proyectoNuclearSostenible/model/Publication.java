package co.edu.cue.proyectoNuclearSostenible.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "publication")
public class Publication implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPublication;

    @ManyToOne
    @JoinColumn(name="id_user")
    private User buyer;

    @ManyToOne
    @JoinColumn(name="id_user")
    private User owner;


    @ManyToOne
    @JoinColumn(name="id_product")
    @NotNull(message = "El producto no puede ser nulo o vacio")
    private Product product;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "date", nullable = false)
    private Date dateCreated;

    @ManyToOne
    @JoinColumn(name="id_state")
    @NotNull(message = "El estado no puede estar vacio")
    private State state;


}
