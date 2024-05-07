package co.edu.cue.proyectoNuclearSostenible.infraestructure.dao;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Publication;
import co.edu.cue.proyectoNuclearSostenible.mapping.mapper.PublicationMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@AllArgsConstructor
public class PublicationRepository {

    @PersistenceContext
    private EntityManager entityManager;


    private PublicationMapper publicationMapper;

    public List<Publication> searchPublications(String title, String productName, String productDescription, String categoryTitle, String stateDescription) {
        // Verificar si se proporcionan parámetros de búsqueda
        if (title == null && productName == null && productDescription == null && categoryTitle == null && stateDescription == null) {
            // No se proporcionaron parámetros, devolver un mensaje o lanzar una excepción
            throw new IllegalArgumentException("Debe proporcionar al menos un parámetro de búsqueda.");
        }

        String queryString = "SELECT DISTINCT p FROM Publication p JOIN p.product pr ";

        if (categoryTitle != null && !categoryTitle.isEmpty()) {
            queryString += " JOIN pr.productCategory c ";
        }

        if (stateDescription != null && !stateDescription.isEmpty()) {
            queryString += " JOIN p.state s ";
        }

        queryString += " WHERE 1=1";

        if (title != null && !title.isEmpty()) {
            queryString += " AND p.title LIKE :title";
        }

        if (productName != null && !productName.isEmpty()) {
            queryString += " AND pr.name LIKE :productName";
        }

        if (productDescription != null && !productDescription.isEmpty()) {
            queryString += " AND pr.description LIKE :productDescription";
        }

        if (categoryTitle != null && !categoryTitle.isEmpty()) {
            queryString += " AND c.title LIKE :categoryTitle";
        }

        if (stateDescription != null && !stateDescription.isEmpty()) {
            queryString += " AND s.description LIKE :stateDescription";
        }

        TypedQuery<Publication> query = entityManager.createQuery(queryString, Publication.class);

        if (title != null && !title.isEmpty()) {
            query.setParameter("title", "%" + title + "%");
        }

        if (productName != null && !productName.isEmpty()) {
            query.setParameter("productName", "%" + productName + "%");
        }

        if (productDescription != null && !productDescription.isEmpty()) {
            query.setParameter("productDescription", "%" + productDescription + "%");
        }

        if (categoryTitle != null && !categoryTitle.isEmpty()) {
            query.setParameter("categoryTitle", "%" + categoryTitle + "%");
        }

        if (stateDescription != null && !stateDescription.isEmpty()) {
            query.setParameter("stateDescription", "%" + stateDescription + "%");
        }

        return query.getResultList();
    }




}
