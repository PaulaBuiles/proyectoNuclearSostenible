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

    /*public List<Publication> searchPublications(String category, String title, String name, String description, String state) {
        String queryString = "SELECT p FROM Publication p JOIN p.product pr JOIN pr.productCategory pc WHERE 1=1";

        if (category != null && !category.isEmpty()) {
            queryString += " AND pc.title = :category";
        }
        if (title != null && !title.isEmpty()) {
            queryString += " AND p.title LIKE :title";
        }
        if (name != null && !name.isEmpty()) {
            queryString += " AND p.name LIKE :name";
        }
        if (description != null && !description.isEmpty()) {
            queryString += " AND p.description LIKE :description";
        }
        if (state != null && !state.isEmpty()) {
            queryString += " AND p.state = :state";
        }

        TypedQuery<Publication> query = entityManager.createQuery(queryString, Publication.class);

        if (category != null && !category.isEmpty()) {
            query.setParameter("category", category);
        }
        if (title != null && !title.isEmpty()) {
            query.setParameter("title", "%" + title + "%");
        }
        if (name != null && !name.isEmpty()) {
            query.setParameter("name", "%" + name + "%");
        }
        if (description != null && !description.isEmpty()) {
            query.setParameter("description", "%" + description + "%");
        }
        if (state != null && !state.isEmpty()) {
            query.setParameter("state", state);
        }

        return query.getResultList();
    }*/

    public List<Publication> searchPublications(String title, String productName, String productDescription, String categoryTitle, String stateDescription) {
        String queryString = "SELECT DISTINCT p FROM Publication p JOIN p.product pr ";

        if (categoryTitle != null && !categoryTitle.isEmpty()) {
            queryString += " JOIN pr.category c ";
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
