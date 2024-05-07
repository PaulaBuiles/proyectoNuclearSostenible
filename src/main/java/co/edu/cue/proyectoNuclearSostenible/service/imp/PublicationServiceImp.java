package co.edu.cue.proyectoNuclearSostenible.service.imp;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Publication;
import co.edu.cue.proyectoNuclearSostenible.domain.entities.State;
import co.edu.cue.proyectoNuclearSostenible.domain.entities.User;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.PublicationDao;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.PublicationDto;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.UserDto;
import co.edu.cue.proyectoNuclearSostenible.mapping.mapper.PublicationMapper;
import co.edu.cue.proyectoNuclearSostenible.service.PublicationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PublicationServiceImp implements PublicationService {

    @Qualifier("publicationMapper")
    private PublicationMapper mapper;

    private PublicationDao publicationDao;

    private UserServiceImp userService;
    private ProductServiceImp productService;
    private StateServiceImpl stateService;
    @Override
    public PublicationDto createPublication(PublicationDto publicationDto) {
        validatePublicationInfo(publicationDto);
        Publication publication = mapper.mapToEntity(publicationDto);
        publication.setState(stateService.getById(publicationDto.stateId()));
        publication.setOwner(userService.getById(publicationDto.ownerId()));
        publication.setProduct(productService.getById(publicationDto.productId()));
        return mapper.mapToDTO(publicationDao.save(publication));
    }

    private void validatePublicationInfo(PublicationDto publicationDto) {

        String title = publicationDto.title().toLowerCase();
        Long ownerId = publicationDto.ownerId();


        List<Publication> existingPublications = publicationDao.findByTitleIgnoreCaseAndOwner_IdUser(title,ownerId);

        if (!existingPublications.isEmpty()) {
            throw new IllegalArgumentException("Ya existe una publicación para este producto y este usuario.");
        }
    }

    @Override
    public Publication getPublication(PublicationDto publicationDto) {
        return null;
    }

    public void purchasePublication(Long publicationId, UserDto buyerDto, Long state) {
        // Obtener la publicación por su ID
        Publication publication = publicationDao.findById(publicationId)
                .orElseThrow(() -> new IllegalArgumentException("La publicación con ID " + publicationId + " no fue encontrada."));

        // Validar si la publicación ya fue comprada
        if (publication.getState().getStatus() == false) {
            throw new IllegalStateException("La publicación no esta disponible.");
        }

        // Guardar la publicación con el estado inicial (no vendido)
        publication = publicationDao.save(publication);

        // Obtener el comprador (buyer) a partir del DTO
        User buyer = userService.getUser(buyerDto);

        // Actualizar la publicación con el comprador y el nuevo estado (Vendido)
        publication.setBuyer(buyer);
        State soldState = stateService.getById(state);
        publication.setState(soldState);

        // Guardar los cambios en la base de datos
        publicationDao.save(publication);
    }

}
