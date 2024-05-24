package co.edu.cue.proyectoNuclearSostenible.service.imp;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Assessment;
import co.edu.cue.proyectoNuclearSostenible.domain.entities.Transaction;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.AssessmentDao;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.TransactionDao;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.AssessmentDto;
import co.edu.cue.proyectoNuclearSostenible.mapping.mapper.AssessmentMapper;
import co.edu.cue.proyectoNuclearSostenible.service.AssesmentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AssessmentServiceImpl implements AssesmentService {

    @Autowired
    private AssessmentDao assessmentDao;

    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private AssessmentMapper assessmentMapper;

    @Override
    /**
     * Añade una calificación (Assessment) a una transacción específica.
     *
     * @param transactionId ID de la transacción a la que se añadirá la calificación.
     * @param assessmentDto DTO de la calificación a añadir.
     * @return DTO de la calificación añadida.
     */
    public AssessmentDto addAssessment(Long transactionId, AssessmentDto assessmentDto) {
        Transaction transaction = transactionDao.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transacccion no encontrada"));

        Assessment assessment = assessmentMapper.mapToEntity(assessmentDto);
        assessment.setTransaction(transaction);

        Assessment savedAssessment = assessmentDao.save(assessment);

        return assessmentMapper.mapToDTO(savedAssessment);
    }


}
