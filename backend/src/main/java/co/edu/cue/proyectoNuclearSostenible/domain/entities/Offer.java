package co.edu.cue.proyectoNuclearSostenible.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "offer", indexes = @Index(columnList = "idOffer"))
public class Offer implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOffer;

    @ManyToOne
    @JoinColumn(name = "publication_id", nullable = false)
    private Publication publication;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product exchangedProduct;

    @ManyToOne
    @JoinColumn(name = "offerer_id", nullable = false)
    private User offerer;

    @Column(name = "monetary_value")
    private Double monetaryValue;

    @Column(name = "offer_date", nullable = false)
    private Date offerDate;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "offer")
    private Transaction transaction;

}
