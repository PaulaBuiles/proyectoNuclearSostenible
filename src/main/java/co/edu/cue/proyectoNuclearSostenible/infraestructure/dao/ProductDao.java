package co.edu.cue.proyectoNuclearSostenible.infraestructure.dao;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Product;
import co.edu.cue.proyectoNuclearSostenible.domain.entities.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductDao extends JpaRepository<Product,Long> {

    List<Product> findByNameIgnoreCaseOrUser_IdUser(String name, Long userId);

    @Query("SELECT t FROM Product t WHERE t.idProduct = :id")
    Product findProductById(@Param("id") Long id);

}
