package co.edu.cue.proyectoNuclearSostenible.infraestructure.controller;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Publication;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.PublicationRepository;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.ProductDto;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.PublicationDto;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.UserDto;
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
    public List<Publication> searchPublications(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String productDescription,
            @RequestParam(required = false) String categoryTitle,
            @RequestParam(required = false) String stateDescription) {

        return publicationService.searchPublications(title, productName, productDescription, categoryTitle, stateDescription);
    }
}
