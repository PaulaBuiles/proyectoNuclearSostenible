package co.edu.cue.proyectoNuclearSostenible.mapping.dto;

import java.util.Date;

public record TransactionDto(
        Long idTransaction,
        Long offerId,
        Date transactionDate
) {}
