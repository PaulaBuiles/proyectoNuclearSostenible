package co.edu.cue.proyectoNuclearSostenible.infraestructure.controller;

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
     *
     * @param transactionId ID de la transacción.
     * @param assessmentDto DTO de la calificación a añadir.
     * @return DTO de la calificación añadida.
     */
    @PostMapping("/add")
    public ResponseEntity<?> addAssessment(@RequestParam Long transactionId, @RequestBody AssessmentDto assessmentDto) {
        try {
            AssessmentDto result = assesmentService.addAssessment(transactionId, assessmentDto);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

}
