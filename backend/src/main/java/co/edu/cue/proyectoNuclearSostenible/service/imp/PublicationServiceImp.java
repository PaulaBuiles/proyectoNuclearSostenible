package co.edu.cue.proyectoNuclearSostenible.service.imp;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.*;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.PublicationDao;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.PublicationRepository;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.PublicationDto;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.UserDto;
import co.edu.cue.proyectoNuclearSostenible.mapping.mapper.PublicationMapper;
import co.edu.cue.proyectoNuclearSostenible.mapping.mapper.UserMapper;
import co.edu.cue.proyectoNuclearSostenible.service.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@AllArgsConstructor
public class PublicationServiceImp implements PublicationService {

    @Qualifier("publicationMapper")
    private PublicationMapper mapper;

    @Qualifier("userMapper")
    private UserMapper userMapper;

    @Autowired
    private PublicationRepository publicationRepository;
    @Autowired
    private PublicationDao publicationDao;

    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private StateService stateService;
    @Autowired
    private RewardService rewardService;

    /**
     * Crea una nueva publicación en el sistema y otorga puntos al usuario propietario por la creación de la publicación.
     *
     * @param publicationDto Los datos de la publicación a crear.
     * @return El DTO de la publicación creada.
     * @throws IllegalArgumentException Si ya existe una publicación para el mismo título y usuario propietario.
     */
    @Override
    public PublicationDto createPublication(PublicationDto publicationDto) {

        validatePublicationInfo(publicationDto);

        Publication publication = mapper.mapToEntity(publicationDto);

        State pendingState = stateService.getById(3L);
        publication.setState(pendingState);

        User owner = userService.getById(publicationDto.ownerId());
        publication.setOwner(owner);

        publication.setProduct(productService.getById(publicationDto.productId()));

        Publication savedPublication = publicationDao.save(publication);

        rewardService.addPoints(
                userMapper.mapToDTO(owner),
                10,
                "Puntos otorgados por crear una publicación con el título: "
                        + publication.getTitle()
                        + ". Usuario: " + owner.getUsername()
                        + ", ID de Publicación: " + savedPublication.getIdPublication()
        );

        return mapper.mapToDTO(savedPublication);
    }

    /**
     * Valida la información de la publicación antes de crearla.
     *
     * @param publicationDto Los datos de la publicación a validar.
     * @throws IllegalArgumentException Si ya existe una publicación para el mismo título y usuario propietario.
     */
    private void validatePublicationInfo(PublicationDto publicationDto) {

        String title = publicationDto.title().toLowerCase();

        Long ownerId = publicationDto.ownerId();

        List<Publication> existingPublications = publicationDao.findByTitleIgnoreCaseAndOwner_IdUser(title, ownerId);

        if (!existingPublications.isEmpty()) {
            throw new IllegalArgumentException("Ya existe una publicación para este título y este usuario.");
        }
    }

    @Override
    public Publication getPublication(PublicationDto publicationDto) {
        return null;
    }

    public void purchasePublication(Long publicationId, UserDto buyerDto, Long state) {

        Publication publication = publicationDao.findById(publicationId)
                .orElseThrow(() -> new IllegalArgumentException("La publicación con ID " + publicationId + " no fue encontrada."));

        if (!publication.getState().getStatus()) {
            throw new IllegalStateException("La publicación no esta disponible.");
        }

        publication = publicationDao.save(publication);

        User buyer = userService.getUser(buyerDto);

        publication.setOwner(buyer);
        State soldState = stateService.getById(state);
        publication.setState(soldState);

        publicationDao.save(publication);
    }

    /**
     * Busca publicaciones en el sistema según los parámetros especificados.
     *
     * @param title Título de la publicación.
     * @param productName Nombre del producto asociado a la publicación.
     * @param productDescription Descripción del producto asociado a la publicación.
     * @param categoryTitle Título de la categoría del producto asociado a la publicación.
     * @param stateDescription Descripción del estado de la publicación.
     * @return Lista de publicaciones que cumplen con los criterios de búsqueda.
     */
    public List<Publication> searchPublications(
            String title,
            String productName,
            String productDescription,
            String categoryTitle,
            String stateDescription) {
        return publicationRepository.searchPublications(title, productName, productDescription, categoryTitle, stateDescription);
    }

    public List<Publication> getPublicationByUserId(Long id) {
        return publicationDao.findByOwner_IdUser(id);
    }

    public List<Publication> getPublicationByProductId(Long id) {
        return publicationDao.findPublicationByProduct_IdProduct(id);
    }

    /**
     * Obtiene todas las ofertas para una publicación específica.
     *
     * @param publicationId El ID de la publicación.
     * @return Lista de ofertas para la publicación.
     */
    public List<Offer> getOffersByPublicationId(Long publicationId) {
        Publication publication = publicationDao.findById(publicationId)
                .orElseThrow(() -> new IllegalArgumentException("La publicación con ID " + publicationId + " no fue encontrada."));

        return publication.getOffers();
    }

    /**
     * Obtiene la transacción de una oferta asociada a una publicación específica.
     *
     * @param publicationId El ID de la publicación.
     * @return La transacción de la oferta.
     * @throws NoSuchElementException Si no se encuentra una oferta con una transacción no nula.
     */
    public Transaction getTransactionByPublicationId(Long publicationId) {
        Publication publication = publicationDao.findById(publicationId)
                .orElseThrow(() -> new NoSuchElementException("La publicación con ID " + publicationId + " no fue encontrada."));

        return publication.getOffers().stream()
                .map(Offer::getTransaction)
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No se encontró una oferta con una transacción no nula."));
    }

    /**
     * Edita una publicación existente en el sistema.
     *
     * @param publicationId El ID de la publicación a editar.
     * @param publicationDto Los datos actualizados de la publicación.
     * @return El DTO de la publicación editada.
     * @throws NoSuchElementException Si la publicación con el ID proporcionado no se encuentra.
     */
    public PublicationDto editPublication(Long publicationId, PublicationDto publicationDto) {
        Publication existingPublication = publicationDao.findById(publicationId)
                .orElseThrow(() -> new NoSuchElementException("Publicación no encontrada con el ID " + publicationId));

        existingPublication.setTitle(publicationDto.title());
        existingPublication.setDateCreated(publicationDto.dateCreated());
        existingPublication.setState(stateService.getById(publicationDto.stateId()));
        existingPublication.setOwner(userService.getById(publicationDto.ownerId()));
        existingPublication.setProduct(productService.getById(publicationDto.productId()));

        return mapper.mapToDTO(publicationDao.save(existingPublication));
    }


}



