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

    @Autowired
    private PublicationRepository publicationRepository;

    @PostMapping(headers = "Accept=application/json")
    public ResponseEntity<?> createPublication (@RequestBody PublicationDto publicationDto) {
        try {
            return new ResponseEntity<>(publicationService.createPublication(publicationDto), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/buscarPublicaciones")
    public List<Publication> buscarPublicaciones(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String productDescription,
            @RequestParam(required = false) String categoryTitle,
            @RequestParam(required = false) String stateDescription) {

        return publicationRepository.searchPublications(title, productName, productDescription, categoryTitle, stateDescription);
    }
}
