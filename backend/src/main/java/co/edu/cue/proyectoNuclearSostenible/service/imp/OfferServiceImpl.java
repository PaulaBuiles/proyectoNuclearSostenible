package co.edu.cue.proyectoNuclearSostenible.service.imp;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Offer;
import co.edu.cue.proyectoNuclearSostenible.domain.entities.Publication;
import co.edu.cue.proyectoNuclearSostenible.domain.entities.Transaction;
import co.edu.cue.proyectoNuclearSostenible.domain.entities.User;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.OfferDao;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.PublicationDao;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.StateDao;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.TransactionDao;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.OfferDto;
import co.edu.cue.proyectoNuclearSostenible.mapping.mapper.OfferMapper;
import co.edu.cue.proyectoNuclearSostenible.service.OfferService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class OfferServiceImpl implements OfferService {


    private OfferDao offerDao;
    private PublicationDao publicationDao;
    private TransactionDao transactionDao;
    private OfferMapper offerMapper;
    private StateDao stateDao;

    /**
     * Crea una nueva oferta.
     *
     * @param offerDto DTO de la oferta a crear.
     * @return DTO de la oferta creada.
     */
    @Override
    public OfferDto createOffer(OfferDto offerDto) {
        Offer offer = offerMapper.mapToEntity(offerDto);
        Offer savedOffer = offerDao.save(offer);
        return offerMapper.mapToDTO(savedOffer);
    }

    /**
     * Acepta una oferta creando una transacción asociada y actualizando el estado de la publicación.
     *
     * @param offerId ID de la oferta a aceptar.
     * @return DTO de la oferta aceptada.
     */
    @Override
    public OfferDto acceptOffer(Long offerId) {
        Offer offer = getOfferOrThrow(offerId);

        Transaction transaction = createTransactionForOffer(offer);
        offer.setTransaction(transaction);
        offerDao.save(offer);

        //Se supone que 1L es el State que esta en "Vendido"
        updatePublicationStatus(offer.getPublication(), 1L);

        return offerMapper.mapToDTO(offer);
    }

    /**
     * Rechaza una oferta.
     *
     * @param offerId ID de la oferta a rechazar.
     * @return DTO de la oferta rechazada.
     */
    @Override
    public OfferDto rejectOffer(Long offerId) {
        Offer offer = getOfferOrThrow(offerId);
        offer.setTransaction(null);
        Offer savedOffer = offerDao.save(offer);
        return offerMapper.mapToDTO(savedOffer);
    }

    /**
     * Obtiene todas las ofertas asociadas a una publicación.
     *
     * @param publicationId ID de la publicación.
     * @return Lista de DTOs de ofertas.
     */
    public List<Offer> getOffersByPublication(Long publicationId) {
        return offerDao.findOffersByPublicationId(publicationId);
    }

    /**
     * Obtiene todas las ofertas realizadas por un usuario.
     *
     * @param userId ID del usuario.
     * @return Lista de DTOs de ofertas.
     */
    public List<Offer> getOffersByUser(Long userId) {
        return offerDao.findOfferById(userId);
    }


    /**
     * Obtiene una oferta por su ID o lanza una excepción si no se encuentra.
     *
     * @param offerId ID de la oferta.
     * @return La oferta encontrada.
     */
    private Offer getOfferOrThrow(Long offerId) {
        return offerDao.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offer not found"));
    }

    /**
     * Crea una transacción para una oferta.
     *
     * @param offer La oferta para la que se creará la transacción.
     * @return La transacción creada.
     */
    private Transaction createTransactionForOffer(Offer offer) {
        Transaction transaction = new Transaction();
        transaction.setOffer(offer);
        transaction.setTransactionDate(new Date());
        return transactionDao.save(transaction);
    }

    /**
     * Actualiza el estado de una publicación.
     *
     * @param publication La publicación a actualizar.
     * @param status El nuevo estado de la publicación.
     */
    private void updatePublicationStatus(Publication publication, Long status) {
        publication.setState(stateDao.findStateById(status));
        publicationDao.save(publication);
    }
}
