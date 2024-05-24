package co.edu.cue.proyectoNuclearSostenible.infraestructure.dao;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionDao extends JpaRepository<Transaction,Long> {

    @Query("SELECT t FROM Transaction t WHERE t.offer.offerer.idUser = :userId")
    List<Transaction> findAllByUserId(@Param("userId") Long userId);

}
