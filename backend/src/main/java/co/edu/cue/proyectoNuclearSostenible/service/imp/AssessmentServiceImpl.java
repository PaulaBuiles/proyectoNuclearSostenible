package co.edu.cue.proyectoNuclearSostenible.service.imp;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Assessment;
import co.edu.cue.proyectoNuclearSostenible.domain.entities.Transaction;
import co.edu.cue.proyectoNuclearSostenible.domain.entities.User;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.AssessmentDao;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.TransactionDao;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.AssessmentDto;
import co.edu.cue.proyectoNuclearSostenible.mapping.mapper.AssessmentMapper;
import co.edu.cue.proyectoNuclearSostenible.mapping.mapper.UserMapper;
import co.edu.cue.proyectoNuclearSostenible.service.AssesmentService;
import co.edu.cue.proyectoNuclearSostenible.service.RewardService;
import co.edu.cue.proyectoNuclearSostenible.service.UserService;
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

    @Autowired
    private UserService userService;
    @Autowired
    private RewardService rewardService;
    @Autowired
    private UserMapper userMapper;

    @Override
/**
 * Añade una calificación (Assessment) a una transacción específica y otorga puntos al usuario que realiza la calificación.
 *
 * @param transactionId ID de la transacción a la que se añadirá la calificación.
 * @param assessmentDto DTO de la calificación a añadir.
 * @return DTO de la calificación añadida.
 */
    public AssessmentDto addAssessment(Long transactionId, AssessmentDto assessmentDto) {
        Transaction transaction = transactionDao.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada"));

        Assessment assessment = assessmentMapper.mapToEntity(assessmentDto);
        assessment.setTransaction(transaction);

        Assessment savedAssessment = assessmentDao.save(assessment);

        User assessor = userService.getById(transaction.getOffer().getOfferer().getIdUser());

        rewardService.addPoints(
                userMapper.mapToDTO(assessor),
                5,
                "Puntos otorgados por realizar una calificación. " +
                        "ID de Transacción: " + transactionId +
                        ", ID de Calificación: " + savedAssessment.getIdAssessment() +
                        ", Usuario: " + assessor.getUsername()
        );

        return assessmentMapper.mapToDTO(savedAssessment);
    }



}
