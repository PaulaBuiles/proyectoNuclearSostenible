package co.edu.cue.proyectoNuclearSostenible.infraestructure.controller;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Offer;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.OfferDto;
import co.edu.cue.proyectoNuclearSostenible.service.OfferService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offers")
@CrossOrigin(origins = "http://localhost:4200")
public class OfferController {
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
    public OfferDto createOffer(@RequestBody OfferDto offerDto) {
        return offerService.createOffer(offerDto);
    }

    /**
     * Endpoint para aceptar una oferta.
     *
     * @param offerId ID de la oferta a aceptar.
     * @return DTO de la oferta aceptada.
     */
    @PostMapping("/{offerId}/accept")
    public OfferDto acceptOffer(@PathVariable Long offerId) {
        return offerService.acceptOffer(offerId);
    }

    /**
     * Endpoint para rechazar una oferta.
     *
     * @param offerId ID de la oferta a rechazar.
     * @return DTO de la oferta rechazada.
     */
    @PostMapping("/{offerId}/reject")
    public OfferDto rejectOffer(@PathVariable Long offerId) {
        return offerService.rejectOffer(offerId);
    }

    /**
     * Endpoint para obtener todas las ofertas asociadas a una publicación.
     *
     * @param publicationId ID de la publicación.
     * @return Lista de ofertas.
     */
    @GetMapping("/publication/{publicationId}")
    public List<Offer> getOffersByPublication(@PathVariable Long publicationId) {
        return offerService.getOffersByPublication(publicationId);
    }

    /**
     * Endpoint para obtener todas las ofertas realizadas por un usuario.
     *
     * @param userId ID del usuario.
     * @return Lista de ofertas.
     */
    @GetMapping("/user/{userId}")
    public List<Offer> getOffersByUser(@PathVariable Long userId) {
        return offerService.getOffersByUser(userId);
    }

}
