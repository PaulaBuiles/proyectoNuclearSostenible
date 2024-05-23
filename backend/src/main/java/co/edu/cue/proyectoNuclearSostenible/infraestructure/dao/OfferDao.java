package co.edu.cue.proyectoNuclearSostenible.infraestructure.dao;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Offer;
import co.edu.cue.proyectoNuclearSostenible.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferDao extends JpaRepository<Offer,Long> {

    List<Offer> findByPublicationId(Long publicationId);
    List<Offer> findByOffererId(Long offererId);

}
