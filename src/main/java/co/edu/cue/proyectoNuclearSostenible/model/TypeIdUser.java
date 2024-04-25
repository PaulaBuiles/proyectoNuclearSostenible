package co.edu.cue.proyectoNuclearSostenible.model;


import jakarta.persistence.*;

@Entity
@Table(name = "type_id_user")
public class TypeIdUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "code", nullable = false)
    private String code;

}
