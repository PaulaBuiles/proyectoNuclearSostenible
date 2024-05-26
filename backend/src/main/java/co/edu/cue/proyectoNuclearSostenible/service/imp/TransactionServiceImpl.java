package co.edu.cue.proyectoNuclearSostenible.service.imp;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Assessment;
import co.edu.cue.proyectoNuclearSostenible.domain.entities.Offer;
import co.edu.cue.proyectoNuclearSostenible.domain.entities.Transaction;
import co.edu.cue.proyectoNuclearSostenible.domain.entities.User;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.AssessmentDao;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.OfferDao;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.TransactionDao;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.AssessmentDto;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.TransactionDto;
import co.edu.cue.proyectoNuclearSostenible.mapping.mapper.AssessmentMapper;
import co.edu.cue.proyectoNuclearSostenible.mapping.mapper.TransactionMapper;
import co.edu.cue.proyectoNuclearSostenible.mapping.mapper.UserMapper;
import co.edu.cue.proyectoNuclearSostenible.service.AssesmentService;
import co.edu.cue.proyectoNuclearSostenible.service.RewardService;
import co.edu.cue.proyectoNuclearSostenible.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionServiceImpl  implements TransactionService {

    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private OfferDao offerDao;

    @Autowired
    private AssessmentDao assessmentDao;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AssesmentService assesmentService;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private RewardService rewardService;


    @Override
    /**
     * Crea una nueva transacción para una oferta específica, añade una calificación (Assessment) asociada y otorga puntos
     * tanto al propietario de la publicación como al ofertante.
     *
     * @param offerId ID de la oferta para la cual se creará la transacción.
     * @param assessmentDto DTO de la calificación que se añadirá a la transacción.
     * @return DTO de la transacción creada.
     */
    public TransactionDto createTransaction(Long offerId, AssessmentDto assessmentDto) {
        Offer offer = offerDao.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offer not found"));

        Transaction transaction = new Transaction();
        transaction.setOffer(offer);
        transaction.setTransactionDate(new Date());
        Transaction savedTransaction = transactionDao.save(transaction);

        assesmentService.addAssessment(savedTransaction.getIdTransaction(), assessmentDto);

        User owner = offer.getPublication().getOwner();
        User offerer = offer.getOfferer();

        // Añadir puntos al que realizo la publicación
        rewardService.addPoints(
                userMapper.mapToDTO(owner),
                20,
                "Puntos otorgados por completar una transacción como propietario de la publicación. " +
                        "ID de Publicación: " + offer.getPublication().getIdPublication() +
                        ", ID de Transacción: " + savedTransaction.getIdTransaction()
        );

        // Añadir puntos al ofertante
        rewardService.addPoints(
                userMapper.mapToDTO(offerer),
                20,
                "Puntos otorgados por completar una transacción como ofertante. " +
                        "ID de Publicación: " + offer.getPublication().getIdPublication() +
                        ", ID de Transacción: " + savedTransaction.getIdTransaction()
        );

        return transactionMapper.mapToDTO(savedTransaction);
    }


    /**
     * Obtiene una transacción por su ID o lanza una excepción si no se encuentra.
     *
     * @param transactionId ID de la transacción.
     * @return La transacción encontrada.
     */
    public TransactionDto getTransactionOrThrow(Long transactionId) {
        Transaction transaction = transactionDao.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        return transactionMapper.mapToDTO(transaction);
    }

    @Override
    /**
     * Obtiene todas las transacciones realizadas por un usuario específico.
     *
     * @param userId ID del usuario cuyas transacciones se desean recuperar.
     * @return Una lista de DTOs de transacciones asociadas al usuario.
     */
    public List<TransactionDto> getTransactionsByUserId(Long userId) {
        List<Transaction> transactions = transactionDao.findAllByUserId(userId);
        return transactions.stream()
                .map(transactionMapper::mapToDTO)
                .collect(Collectors.toList());
    }
}
