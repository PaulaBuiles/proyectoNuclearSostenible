package co.edu.cue.proyectoNuclearSostenible.infraestructure.controller;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Offer;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.OfferDto;
import co.edu.cue.proyectoNuclearSostenible.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/offers")
@CrossOrigin(origins = "http://localhost:4200")
public class OfferController {

    @Autowired
    private final OfferService offerService;


    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    /**
     * Endpoint para crear una nueva oferta.
     *
     * @param offerDto DTO de la oferta a crear.
     * @return DTO de la oferta creada.
     */
    @PostMapping
    public ResponseEntity<?> createOffer(@RequestBody OfferDto offerDto) {
        try {
            return new ResponseEntity<>(offerService.createOffer(offerDto), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    /**
     * Endpoint para aceptar una oferta.
     *
     * @param offerId ID de la oferta a aceptar.
     * @return DTO de la oferta aceptada.
     */
    @PostMapping("/{offerId}/accept")
    public ResponseEntity<?> acceptOffer(@PathVariable Long offerId) {
        try {
            return new ResponseEntity<>(offerService.acceptOffer(offerId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    /**
     * Endpoint para rechazar una oferta.
     *
     * @param offerId ID de la oferta a rechazar.
     * @return DTO de la oferta rechazada.
     */
    @PostMapping("/{offerId}/reject")
    public ResponseEntity<?> rejectOffer(@PathVariable Long offerId) {
        try {
            return new ResponseEntity<>(offerService.rejectOffer(offerId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    /**
     * Endpoint para obtener todas las ofertas asociadas a una publicación.
     *
     * @param publicationId ID de la publicación.
     * @return Lista de ofertas.
     */
    @GetMapping("/publication/{publicationId}")
    public ResponseEntity<?> getOffersByPublication(@PathVariable Long publicationId) {
        try {
            return new ResponseEntity<>(offerService.getOffersByPublication(publicationId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
    /**
     * Endpoint para obtener todas las ofertas realizadas por un usuario.
     *
     * @param userId ID del usuario.
     * @return Lista de ofertas.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getOffersByUser(@PathVariable Long userId) {
        try {
            return new ResponseEntity<>(offerService.getOffersByUser(userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

}
