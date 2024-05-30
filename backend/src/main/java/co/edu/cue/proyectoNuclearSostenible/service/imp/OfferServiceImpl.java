package co.edu.cue.proyectoNuclearSostenible.service.imp;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.*;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.*;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.OfferDto;
import co.edu.cue.proyectoNuclearSostenible.mapping.mapper.OfferMapper;
import co.edu.cue.proyectoNuclearSostenible.service.OfferService;
import co.edu.cue.proyectoNuclearSostenible.service.StateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class OfferServiceImpl implements OfferService {


    private OfferDao offerDao;
    private PublicationDao publicationDao;
    private ProductDao productDao;
    private TransactionDao transactionDao;
    private OfferMapper offerMapper;
    private StateDao stateDao;
    private UserServiceImp userService;
    private EmailService emailService;
    private StateService stateService;

    /**
     * Crea una nueva oferta.
     *
     * @param offerDto DTO de la oferta a crear.
     * @return DTO de la oferta creada.
     */
    @Override
    public OfferDto createOffer(OfferDto offerDto) {
        Offer offer = offerMapper.mapToEntity(offerDto);

        // Verificar y asignar la entidad User
        User offerer = userService.getById(offerDto.offererId());
        if (offerer == null) {
            throw new IllegalArgumentException("Offerer no encontrado con el ID: " + offerDto.offererId());
        }
        offer.setOfferer(offerer);

        // Verificar y asignar la entidad Publication
        Publication publication = publicationDao.getById(offerDto.publicationId());
        if (publication == null) {
            throw new IllegalArgumentException("Publicación no encontrada con el ID: " + offerDto.publicationId());
        }
        offer.setPublication(publication);

        // Verificar y asignar la entidad Product, si está presente
        if (offerDto.exchangedProductId() != null) {
            Product exchangedProduct = productDao.getById(offerDto.exchangedProductId());
            if (exchangedProduct == null) {
                throw new IllegalArgumentException("Producto intercambiado no encontrado con el ID: " + offerDto.exchangedProductId());
            }
            offer.setExchangedProduct(exchangedProduct);
        }

        State pendingState = stateService.getById(4L);
        offer.setState(pendingState);


        Offer savedOffer = offerDao.save(offer);


        String email = publication.getOwner().getEmail();
        String subject = "Nueva oferta recibida";
        String text = "Has recibido una nueva oferta para tu publicación con título '" + publication.getTitle() + "'.";
        emailService.sendEmail(email, subject, text);

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

        State acceptedState = stateService.getById(5L);
        offer.setState(acceptedState);

        offerDao.save(offer);

        Long stateId;
        if (offer.getExchangedProduct() != null) {
            // Estado "Intercambiado" (ID 2)
            stateId = 2L;
        } else {
            // Estado "Vendido" (ID 1)
            stateId = 1L;
        }

        updatePublicationStatus(offer.getPublication(), stateId);
        String email = offer.getOfferer().getEmail();
        String subject = "Oferta aceptada";
        String text = "Tu oferta para la publicación con título '" + offer.getPublication().getTitle() + "' ha sido aceptada.";
        emailService.sendEmail(email, subject, text);

        return offerMapper.mapToDTO(offer);
    }

    /**
     * Rechaza una oferta y actualiza su estado a "Rechazada".
     *
     * Envía un correo electrónico al ofertante notificando que su oferta ha sido rechazada.
     *
     * @param offerId El ID de la oferta a rechazar.
     * @return El DTO de la oferta rechazada.
     */
    @Override
    public OfferDto rejectOffer(Long offerId) {
        Offer offer = getOfferOrThrow(offerId);

        State rejectedState = stateService.getById(6L);
        offer.setState(rejectedState);

        offer.setTransaction(null);
        Offer savedOffer = offerDao.save(offer);


        String email = offer.getOfferer().getEmail();
        String subject = "Oferta rechazada";
        String text = "Tu oferta para la publicación con título '" + offer.getPublication().getTitle() + "' ha sido rechazada.";
        emailService.sendEmail(email, subject, text);

        return offerMapper.mapToDTO(savedOffer);
    }


    /**
     * Obtiene todas las ofertas asociadas a una publicación.
     *
     * @param publicationId ID de la publicación.
     * @return Lista de DTOs de ofertas.
     */
    public List<OfferDto> getOffersByPublication(Long publicationId) {
        List<Offer> offers = offerDao.findOffersByPublicationId(publicationId);
        List<OfferDto> offerDtos = new ArrayList<>();
        for (Offer offer : offers) {
            Long idTransaction = (offer.getTransaction() != null && offer.getTransaction().getIdTransaction() != null) ? offer.getTransaction().getIdTransaction() : null;
            Long idExchanged = (offer.getExchangedProduct() != null && offer.getExchangedProduct().getIdProduct() != null) ? offer.getExchangedProduct().getIdProduct() : null;
            offerDtos.add(new OfferDto(offer.getIdOffer(), publicationId, idExchanged, offer.getOfferer().getIdUser(),offer.getMonetaryValue(),offer.getOfferDate(),offer.getState().getDescription(), idTransaction));
        }
        return offerDtos;
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
        Transaction savedTransaction = transactionDao.save(transaction);


        String email = offer.getPublication().getOwner().getEmail();
        String subject = "Transacción realizada";
        String text = "Se ha realizado una transacción para tu publicación con título '" + offer.getPublication().getTitle() + "'.";
        emailService.sendEmail(email, subject, text);

        return savedTransaction;
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

    /**
     * Obtiene los estados de las ofertas a partir de su ID.
     *
     * @param offerId El ID de la oferta para el estado a obtener.
     * @return El estado correspondiente al ID proporcionado.
     */
    public State getStateByOfferId(Long offerId) {
        return offerDao.findStateByOfferId(offerId);
    }


}
