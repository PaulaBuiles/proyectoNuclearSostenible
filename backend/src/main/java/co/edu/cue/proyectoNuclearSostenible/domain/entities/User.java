package co.edu.cue.proyectoNuclearSostenible.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "users", indexes = @Index(columnList = "idUser"))
public class User implements Serializable, UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    @Column(name = "userName", nullable = false)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "fullName", nullable = false)
    private String fullName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone", nullable = false)
    private String phone;

    @ManyToOne
    @JoinColumn(name = "id_type_id")
    @NotNull(message = "El tipo de identifiación no puede ser nulo o vacio")
    private TypeId typeIdUser;

    @Column(name = "identification", nullable = false)
    private String identification;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "status", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean status;

    @Column(name = "points", nullable = false, columnDefinition = "INT DEFAULT 0")
    private int points;

    @Column(name = "isAdmin", nullable = false)
    private Boolean isAdmin;
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Product> listProducts;

    @OneToMany(mappedBy = "complainant", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Report> complaintsMade;

    @OneToMany(mappedBy = "denounced", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Report> ownComplaints;

    @ManyToMany
    @JoinTable(name = "user_rewards", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "id_reward"))
    private List<Reward> rewards;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Token> lstToken;

    @OneToMany(mappedBy = "offerer", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Offer> lstOffer;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
            return Set.of(new SimpleGrantedAuthority(Boolean.TRUE.equals(this.isAdmin) ? "ADMIN" : "USER"));
    }

    public String getRol() {
        return (Boolean.TRUE.equals(this.isAdmin) ? "ADMIN" : "USER");
    }

    public void updatePoints(int points) {
        this.points += points;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    //Usuario tiene listdado de productos, publicaciones y compras

}
