package co.edu.cue.proyectoNuclearSostenible.service;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Offer;
import co.edu.cue.proyectoNuclearSostenible.domain.entities.State;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.OfferDto;

import java.util.List;

public interface OfferService {

    OfferDto createOffer(OfferDto offerDto);
    OfferDto acceptOffer(Long offerId);
    OfferDto rejectOffer(Long offerId);
    List<OfferDto> getOffersByPublication(Long publicationId);
    List<Offer> getOffersByUser(Long userId);

    State getStateByOfferId(Long offerId);

}
