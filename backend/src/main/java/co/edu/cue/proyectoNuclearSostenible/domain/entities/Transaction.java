package co.edu.cue.proyectoNuclearSostenible.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "transaction", indexes = @Index(columnList = "idTransaction"))
public class Transaction implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTransaction;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_offer")
    private Offer offer;

    @Column(name = "transaction_date", nullable = false)
    private Date transactionDate;

    @OneToOne(mappedBy = "transaction" ,cascade = CascadeType.ALL)
    private Assessment assessment;

}
