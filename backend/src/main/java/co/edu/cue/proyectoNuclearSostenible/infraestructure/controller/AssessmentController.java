package co.edu.cue.proyectoNuclearSostenible.infraestructure.controller;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Assessment;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.AssessmentDto;
import co.edu.cue.proyectoNuclearSostenible.service.AssesmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assessments")
@CrossOrigin(origins = "http://localhost:4200")
public class AssessmentController {

    @Autowired
    private final AssesmentService assesmentService;

    @Autowired
    public AssessmentController(AssesmentService assesmentService) {
        this.assesmentService = assesmentService;
    }

    /**
     * Endpoint para añadir una calificación (Assessment) a una transacción específica.
     * @param assessmentDto DTO de la calificación a añadir.
     * @return DTO de la calificación añadida.
     */
    @PostMapping("/add")
    public ResponseEntity<?> addAssessment(@RequestBody AssessmentDto assessmentDto) {
        try {
            return new ResponseEntity<>(assesmentService.addAssessment(assessmentDto.transactionId(), assessmentDto), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }


    /**
     * Endpoint para obtener una evaluación (Assessment) basada en el ID de la transacción.
     * @param transactionId ID de la transacción.
     * @return Evaluación (Assessment) asociada a la transacción.
     */
    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<Assessment> getAssessmentByTransactionId(@PathVariable Long transactionId) {
        try {
            Assessment assessment = assesmentService.getAssessmentByTransactionId(transactionId);
            if (assessment != null) {
                return new ResponseEntity<>(assessment, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint para obtener el promedio de las calificaciones (ratings) de un usuario basado en el ID del usuario.
     * @param userId ID del usuario.
     * @return Promedio de las calificaciones del usuario.
     */
    @GetMapping("/average-rating/user/{userId}")
    public ResponseEntity<Double> getAverageRatingByUserId(@PathVariable Long userId) {
        try {
            Double averageRating = assesmentService.getAverageRatingByUserId(userId);
            if (averageRating != null) {
                return new ResponseEntity<>(averageRating, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
