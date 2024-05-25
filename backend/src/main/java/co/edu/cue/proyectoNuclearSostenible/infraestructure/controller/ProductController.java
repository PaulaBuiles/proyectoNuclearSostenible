package co.edu.cue.proyectoNuclearSostenible.infraestructure.controller;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Product;
import co.edu.cue.proyectoNuclearSostenible.domain.entities.Report;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.ProductDto;
import co.edu.cue.proyectoNuclearSostenible.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
@Controller
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * Crea un nuevo producto en el sistema.
     *
     * @param product El DTO del producto a crear (en formato JSON en el cuerpo de la solicitud).
     * @return ResponseEntity con el resultado de la creación del producto.
     *         Si la creación es exitosa, devuelve un ResponseEntity con el cuerpo de la respuesta conteniendo el producto creado y el código de estado HTTP 200 (OK).
     *         Si ocurre un error durante la creación, devuelve un ResponseEntity con el mensaje de error correspondiente y el código de estado HTTP 409 (Conflict).
     */
    @PostMapping(headers = "Accept=application/json")
    public ResponseEntity<?> createProduct(@RequestBody ProductDto product) {
        try {
            return new ResponseEntity<>(productService.createProduct(product), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        try {
            return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/list-user/{id}")
    public ResponseEntity<?> getProductsByUserId(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(productService.getProductByUserId(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(productService.getById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
}
