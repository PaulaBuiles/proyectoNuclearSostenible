package co.edu.cue.proyectoNuclearSostenible.service;

import co.edu.cue.proyectoNuclearSostenible.mapping.dto.AssessmentDto;

public interface AssesmentService {

    AssessmentDto addAssessment(Long transactionId, AssessmentDto assessmentDto);

}
