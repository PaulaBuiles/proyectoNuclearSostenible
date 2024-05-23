package co.edu.cue.proyectoNuclearSostenible.mapping.dto;

public record AssessmentDto (
        Long idAssessment,
        String comment,
        String rating,
        Long transactionId
) {}

