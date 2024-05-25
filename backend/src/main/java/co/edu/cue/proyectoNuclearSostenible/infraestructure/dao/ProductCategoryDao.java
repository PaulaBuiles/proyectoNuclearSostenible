package co.edu.cue.proyectoNuclearSostenible.infraestructure.dao;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Product;
import co.edu.cue.proyectoNuclearSostenible.domain.entities.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductCategoryDao extends JpaRepository<ProductCategory, Long> {

    ProductCategory findByTitleIgnoreCase(String lowercaseTitle);

    @Query("SELECT t FROM ProductCategory t WHERE t.idCategory = :id")
    ProductCategory findProductCategoryById(@Param("id") Long id);
}
