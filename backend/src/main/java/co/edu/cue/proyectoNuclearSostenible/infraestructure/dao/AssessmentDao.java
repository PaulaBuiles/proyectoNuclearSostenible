package co.edu.cue.proyectoNuclearSostenible.infraestructure.dao;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Assessment;
import co.edu.cue.proyectoNuclearSostenible.domain.entities.Offer;
import co.edu.cue.proyectoNuclearSostenible.domain.entities.State;
import co.edu.cue.proyectoNuclearSostenible.domain.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AssessmentDao extends JpaRepository<Assessment,Long> {

    @Query("SELECT a FROM Assessment a WHERE a.transaction.idTransaction = :transactionId")
    Assessment findAssessmentByTransactionId(@Param("transactionId") Long transactionId);

    @Query("SELECT AVG(CAST(a.rating AS double)) FROM Assessment a " +
            "JOIN a.transaction t " +
            "JOIN t.offer o " +
            "JOIN o.publication p " +
            "WHERE p.owner.idUser = :userId")
    Double findAverageRatingByUserId(@Param("userId") Long userId);
}
