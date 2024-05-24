package co.edu.cue.proyectoNuclearSostenible.service;


import co.edu.cue.proyectoNuclearSostenible.domain.entities.Transaction;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.AssessmentDto;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.TransactionDto;

import java.util.List;

public interface TransactionService {

    TransactionDto createTransaction(Long offerId, AssessmentDto assessmentDto);

    TransactionDto getTransactionOrThrow(Long transactionId);

    List<TransactionDto> getTransactionsByUserId(Long userId);
}
