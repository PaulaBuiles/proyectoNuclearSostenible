package co.edu.cue.proyectoNuclearSostenible.domain.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "product", indexes = @Index(columnList = "idProduct"))
public class Product implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduct;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "imageUrl", nullable = false)
    private String imageUrl;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name="id_user")
    @NotNull(message = "El usuario no puede ser nulo o vacio")
    private User user;

    @Column(name = "status", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean status;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Publication> listPublications = new HashSet<>();

    @ManyToOne
    @JoinColumn(name="idCategory")
    @NotNull(message = "La categoria no puede ser nulo o vacio")
    private ProductCategory productCategory;

    @OneToMany(mappedBy = "exchangedProduct", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Offer> lstOffer = new HashSet<>();


}
