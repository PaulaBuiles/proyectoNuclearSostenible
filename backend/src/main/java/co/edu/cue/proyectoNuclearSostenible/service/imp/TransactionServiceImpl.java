package co.edu.cue.proyectoNuclearSostenible.service.imp;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Assessment;
import co.edu.cue.proyectoNuclearSostenible.domain.entities.Transaction;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.AssessmentDao;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.TransactionDao;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.AssessmentDto;
import co.edu.cue.proyectoNuclearSostenible.mapping.mapper.AssessmentMapper;
import co.edu.cue.proyectoNuclearSostenible.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private AssessmentDao assessmentDao;

    @Autowired
    private AssessmentMapper assessmentMapper;

    /**
     * Agrega una calificación a una transacción.
     *
     * @param transactionId ID de la transacción.
     * @param assessmentDto DTO de la calificación.
     * @return DTO de la calificación agregada.
     */
    @Override
    public AssessmentDto addAssessment(Long transactionId, AssessmentDto assessmentDto) {
        Transaction transaction = getTransactionOrThrow(transactionId);

        Assessment assessment = assessmentMapper.mapToEntity(assessmentDto);
        assessment.setTransaction(transaction);

        Assessment savedAssessment = assessmentDao.save(assessment);

        return assessmentMapper.mapToDTO(savedAssessment);
    }

    /**
     * Obtiene una transacción por su ID o lanza una excepción si no se encuentra.
     *
     * @param transactionId ID de la transacción.
     * @return La transacción encontrada.
     */
    private Transaction getTransactionOrThrow(Long transactionId) {
        return transactionDao.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }
}
