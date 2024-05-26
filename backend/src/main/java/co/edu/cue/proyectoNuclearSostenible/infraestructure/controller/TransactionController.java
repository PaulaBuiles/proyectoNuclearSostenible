package co.edu.cue.proyectoNuclearSostenible.infraestructure.controller;

import co.edu.cue.proyectoNuclearSostenible.mapping.dto.AssessmentDto;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.TransactionDto;
import co.edu.cue.proyectoNuclearSostenible.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "http://localhost:4200")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;


    /**
     * Endpoint para crear una nueva transacción.
     *
     * @param offerId ID de la oferta.
     * @param assessmentDto DTO de la calificación asociada.
     * @return DTO de la transacción creada.
     */
    @PostMapping("/create")
    public ResponseEntity<?> createTransaction(@RequestParam Long offerId, @RequestBody AssessmentDto assessmentDto) {
        try {
            TransactionDto transactionDto = transactionService.createTransaction(offerId, assessmentDto);
            return new ResponseEntity<>(transactionDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    /**
     * Endpoint para obtener todas las transacciones realizadas por un usuario.
     *
     * @param userId ID del usuario.
     * @return Lista de DTOs de transacciones.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getTransactionsByUserId(@PathVariable Long userId) {
        try {
            List<TransactionDto> transactions = transactionService.getTransactionsByUserId(userId);
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    /**
     * Endpoint para obtener una transacción por su ID.
     *
     * @param transactionId ID de la transacción.
     * @return DTO de la transacción encontrada.
     */
    @GetMapping("/{transactionId}")
    public ResponseEntity<?> getTransactionById(@PathVariable Long transactionId) {
        try {
            TransactionDto transactionDto = transactionService.getTransactionOrThrow(transactionId);
            return new ResponseEntity<>(transactionDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
}
