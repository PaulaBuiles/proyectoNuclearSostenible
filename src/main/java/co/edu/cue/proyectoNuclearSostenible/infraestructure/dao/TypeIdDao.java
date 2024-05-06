package co.edu.cue.proyectoNuclearSostenible.infraestructure.dao;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.TypeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TypeIdDao extends JpaRepository<TypeId, Long> {

    @Query("SELECT t FROM TypeId t WHERE t.idTypeId = :id")
    TypeId findTypeIdById(@Param("id") Long id);
}
