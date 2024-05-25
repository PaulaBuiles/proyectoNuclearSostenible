package co.edu.cue.proyectoNuclearSostenible.infraestructure.dao;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Publication;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface PublicationDao extends JpaRepository<Publication, Long> {
    List<Publication> findByTitleIgnoreCaseAndOwner_IdUser(String title, Long ownerId);

    List<Publication> findByOwner_IdUser(Long id);

    List<Publication> findPublicationByProduct_IdProduct(Long id);
}
