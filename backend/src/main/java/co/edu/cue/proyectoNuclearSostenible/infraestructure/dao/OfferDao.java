package co.edu.cue.proyectoNuclearSostenible.infraestructure.dao;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Offer;
import co.edu.cue.proyectoNuclearSostenible.domain.entities.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OfferDao extends JpaRepository<Offer,Long> {

    @Query("SELECT t FROM Offer t WHERE t.offerer.idUser = :id")
    List<Offer> findOfferById(@Param("id") Long id);

    @Query("SELECT o FROM Offer o WHERE o.publication.idPublication = :publicationId")
    List<Offer> findOffersByPublicationId(@Param("publicationId") Long publicationId);

}
