package co.edu.cue.proyectoNuclearSostenible.infraestructure.controller;

import co.edu.cue.proyectoNuclearSostenible.mapping.dto.AssessmentDto;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.TransactionDto;
import co.edu.cue.proyectoNuclearSostenible.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "http://localhost:4200")
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * Endpoint para crear una nueva transacción.
     *
     * @param offerId ID de la oferta.
     * @param assessmentDto DTO de la calificación asociada.
     * @return DTO de la transacción creada.
     */
    @PostMapping("/create")
    public TransactionDto createTransaction(@RequestParam Long offerId, @RequestBody AssessmentDto assessmentDto) {
        return transactionService.createTransaction(offerId, assessmentDto);
    }

    /**
     * Endpoint para obtener todas las transacciones realizadas por un usuario.
     *
     * @param userId ID del usuario.
     * @return Lista de DTOs de transacciones.
     */
    @GetMapping("/user/{userId}")
    public List<TransactionDto> getTransactionsByUserId(@PathVariable Long userId) {
        return transactionService.getTransactionsByUserId(userId);
    }

    /**
     * Endpoint para obtener una transacción por su ID.
     *
     * @param transactionId ID de la transacción.
     * @return DTO de la transacción encontrada.
     */
    @GetMapping("/{transactionId}")
    public TransactionDto getTransactionById(@PathVariable Long transactionId) {
        return transactionService.getTransactionOrThrow(transactionId);
    }
}
