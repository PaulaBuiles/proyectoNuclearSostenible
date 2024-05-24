package co.edu.cue.proyectoNuclearSostenible.service.imp;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.*;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.*;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.OfferDto;
import co.edu.cue.proyectoNuclearSostenible.mapping.mapper.OfferMapper;
import co.edu.cue.proyectoNuclearSostenible.service.OfferService;
import co.edu.cue.proyectoNuclearSostenible.service.PublicationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OfferServiceImpl implements OfferService {


    private OfferDao offerDao;
    private PublicationDao publicationDao;
    private ProductDao productDao;
    private PublicationService publicationService;
    private TransactionDao transactionDao;
    private OfferMapper offerMapper;
    private StateDao stateDao;
    private UserServiceImp userService;

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

        // Guardar la oferta y devolver el DTO
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
