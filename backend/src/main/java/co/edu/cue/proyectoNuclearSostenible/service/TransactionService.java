package co.edu.cue.proyectoNuclearSostenible.service;

import co.edu.cue.proyectoNuclearSostenible.mapping.dto.AssessmentDto;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.TransactionDto;

public interface TransactionService {
    AssessmentDto addAssessment(Long transactionId, AssessmentDto assessmentDto);
}
