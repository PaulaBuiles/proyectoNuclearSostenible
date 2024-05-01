package co.edu.cue.proyectoNuclearSostenible.controller;

import co.edu.cue.proyectoNuclearSostenible.mapping.dto.ProductoDto;
import co.edu.cue.proyectoNuclearSostenible.model.Product;
import co.edu.cue.proyectoNuclearSostenible.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@Controller
public class ProductController {

    @Autowired
    private ProductService productService;




    @PostMapping
    public ResponseEntity<ProductoDto> createProduct(@Valid @RequestBody Product product){
        return new;
    }

    // Endpoint para actualizar un producto existente
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody Product productDetails){
        return new;
    }

    // Endpoint para eliminar un producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        return new;
    }

}
