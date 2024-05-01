package co.edu.cue.proyectoNuclearSostenible.model;


import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "type_id", indexes = @Index(columnList = "idTypeId"))
public class TypeId implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTypeId;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "code", nullable = false)
    private String code;

    @OneToMany(mappedBy = "typeId", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private Set<User> listUsers = new HashSet<User>();

    @Column(name = "status", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 1")
    private Boolean status;



}
