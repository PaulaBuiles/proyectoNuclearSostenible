package co.edu.cue.proyectoNuclearSostenible.mapping.dto;

import java.util.Date;

public record OfferDto(
        Long idOffer,
        Long publicationId,
        Long exchangedProductId,
        Long offererId,
        Double monetaryValue,
        Date offerDate,
        String state,
        Long transactionId // Agregar para mantener la referencia a la transacción si existe
) {}
