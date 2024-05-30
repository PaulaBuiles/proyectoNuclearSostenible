package co.edu.cue.proyectoNuclearSostenible.infraestructure.controller;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Offer;
import co.edu.cue.proyectoNuclearSostenible.domain.entities.Publication;
import co.edu.cue.proyectoNuclearSostenible.domain.entities.Transaction;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.PublicationDto;
import co.edu.cue.proyectoNuclearSostenible.service.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publication")
@Controller
@CrossOrigin(origins = "http://localhost:4200")
public class PublicationController {

    @Autowired
    private PublicationService publicationService;

    /**
     * Crea una nueva publicación en el sistema.
     *
     * @param publicationDto Los datos de la publicación a crear (en formato JSON en el cuerpo de la solicitud).
     * @return ResponseEntity con el resultado de la creación de la publicación.
     *         Si la creación es exitosa, devuelve un ResponseEntity con el cuerpo de la respuesta conteniendo la publicación creada y el código de estado HTTP 200 (OK).
     *         Si ocurre un error durante la creación, devuelve un ResponseEntity con el mensaje de error correspondiente y el código de estado HTTP 409 (Conflict).
     */
    @PostMapping(headers = "Accept=application/json")
    public ResponseEntity<?> createPublication (@RequestBody PublicationDto publicationDto) {
        try {
            return new ResponseEntity<>(publicationService.createPublication(publicationDto), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    /**
     * Busca publicaciones en el sistema según los parámetros especificados.
     *
     * @param title            Título de la publicación.
     * @param productName      Nombre del producto asociado a la publicación.
     * @param productDescription Descripción del producto asociado a la publicación.
     * @param categoryTitle    Título de la categoría del producto asociado a la publicación.
     * @param stateDescription Descripción del estado de la publicación.
     * @return Lista de publicaciones que cumplen con los criterios de búsqueda.
     */
    @GetMapping("/searchPublications")
    public ResponseEntity<?> searchPublications(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String productDescription,
            @RequestParam(required = false) String categoryTitle,
            @RequestParam(required = false) String stateDescription) {
        try {
            List<Publication> publications = publicationService.searchPublications(title, productName, productDescription, categoryTitle, stateDescription);
            return new ResponseEntity<>(publications, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    /**
     * Obtiene todas las publicaciones asociadas a un usuario específico.
     *
     * Este método maneja las solicitudes GET para obtener una lista de publicaciones
     * asociadas a un usuario identificado por su ID.
     *
     * @param id El ID del usuario cuyas publicaciones se desean obtener.
     * @return Una ResponseEntity que contiene la lista de publicaciones asociadas al usuario
     *         y el código de estado HTTP 200 (OK) si la operación es exitosa,
     *         o un mensaje de error y el código de estado HTTP 409 (Conflict) si ocurre un error.
     */
    @GetMapping("/list-user/{id}")
    public ResponseEntity<?> getPublicationByUserId(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(publicationService.getPublicationByUserId(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }


    /**
     * Obtiene todas las publicaciones asociadas a un producto específico.
     *
     * Este método maneja las solicitudes GET para obtener una lista de publicaciones
     * asociadas a un producto identificado por su ID.
     *
     * @param id El ID del producto cuyas publicaciones se desean obtener.
     * @return Una ResponseEntity que contiene la lista de publicaciones asociadas al producto
     *         y el código de estado HTTP 200 (OK) si la operación es exitosa,
     *         o un mensaje de error y el código de estado HTTP 409 (Conflict) si ocurre un error.
     */
    @GetMapping("/search-by-product/{id}")
    public ResponseEntity<?> getPublicationByProductId(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(publicationService.getPublicationByProductId(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }


    /**
     * Obtiene todas las ofertas para una publicación específica.
     *
     * @param publicationId El ID de la publicación.
     * @return ResponseEntity con la lista de ofertas y el código de estado HTTP 200 (OK).
     *         Si ocurre un error, devuelve un ResponseEntity con el mensaje de error correspondiente y el código de estado HTTP 404 (Not Found).
     */
    @GetMapping("/{publicationId}/offers")
    public ResponseEntity<?> getOffersByPublicationId(@PathVariable Long publicationId) {
        try {
            List<Offer> offers = publicationService.getOffersByPublicationId(publicationId);
            return new ResponseEntity<>(offers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint para obtener la transacción de una oferta asociada a una publicación específica.
     *
     * Este método maneja las solicitudes GET para obtener la transacción correspondiente a una oferta de una
     * publicación dada. Se busca la primera oferta asociada a la publicación que tenga una transacción no nula.
     *
     * @param publicationId El ID de la publicación para la cual se desea obtener la transacción.
     * @return ResponseEntity con la transacción encontrada y el código de estado HTTP 200 (OK).
     *         Si ocurre un error (por ejemplo, si no se encuentra la publicación o ninguna oferta con una transacción no nula),
     *         devuelve un ResponseEntity con el mensaje de error correspondiente y el código de estado HTTP 404 (Not Found).
     */
    @GetMapping("/{publicationId}/transaction")
    public ResponseEntity<?> getTransactionByPublicationId(@PathVariable Long publicationId) {
        try {
            Transaction transaction = publicationService.getTransactionByPublicationId(publicationId);
            return new ResponseEntity<>(transaction, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Edita una publicación existente en el sistema.
     *
     * @param publicationId El ID de la publicación a editar.
     * @param publicationDto Los datos actualizados de la publicación.
     * @return ResponseEntity con la publicación editada y el código de estado HTTP 200 (OK).
     *         Si ocurre un error, devuelve un ResponseEntity con el mensaje de error correspondiente y el código de estado HTTP 404 (Not Found).
     */
    @PutMapping("/{publicationId}")
    public ResponseEntity<?> editPublication(@PathVariable Long publicationId, @RequestBody PublicationDto publicationDto) {
        try {
            PublicationDto updatedPublication = publicationService.editPublication(publicationId, publicationDto);
            return new ResponseEntity<>(updatedPublication, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
