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

    /**
     * Obtiene todos los productos disponibles.
     *
     * Este método maneja las solicitudes GET para obtener una lista de todos los productos.
     *
     * @return Una ResponseEntity que contiene la lista de todos los productos y el código de estado HTTP 200 (OK)
     *         si la operación es exitosa, o un mensaje de error y el código de estado HTTP 409 (Conflict) si ocurre un error.
     */
    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        try {
            return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }


    /**
     * Obtiene todos los productos asociados a un usuario específico.
     *
     * Este método maneja las solicitudes GET para obtener una lista de productos
     * asociados a un usuario identificado por su ID.
     *
     * @param id El ID del usuario cuyos productos se desean obtener.
     * @return Una ResponseEntity que contiene la lista de productos asociados al usuario
     *         y el código de estado HTTP 200 (OK) si la operación es exitosa,
     *         o un mensaje de error y el código de estado HTTP 409 (Conflict) si ocurre un error.
     */
    @GetMapping("/list-user/{id}")
    public ResponseEntity<?> getProductsByUserId(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(productService.getProductByUserId(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }


    /**
     * Obtiene un producto específico por su ID.
     *
     * Este método maneja las solicitudes GET para obtener un producto identificado por su ID.
     *
     * @param id El ID del producto que se desea obtener.
     * @return Una ResponseEntity que contiene el producto correspondiente al ID proporcionado
     *         y el código de estado HTTP 200 (OK) si la operación es exitosa,
     *         o un mensaje de error y el código de estado HTTP 409 (Conflict) si ocurre un error.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(productService.getById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }


    /**
     * Obtiene la última publicación de un producto.
     *
     * @param id El ID del producto.
     * @return ResponseEntity con el resultado de la consulta de la última publicación del producto.
     *         Si la consulta es exitosa, devuelve un ResponseEntity con el cuerpo de la respuesta conteniendo la última publicación y el código de estado HTTP 200 (OK).
     *         Si ocurre un error durante la consulta, devuelve un ResponseEntity con el mensaje de error correspondiente y el código de estado HTTP 404 (Not Found).
     */
    @GetMapping("/{id}/last-publication")
    public ResponseEntity<?> getLastPublication(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(productService.getLastPublication(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Edita un producto existente en el sistema.
     *
     * @param productId El ID del producto a editar.
     * @param productDto Los datos actualizados del producto.
     * @return ResponseEntity con el producto editado y el código de estado HTTP 200 (OK).
     *         Si ocurre un error, devuelve un ResponseEntity con el mensaje de error correspondiente y el código de estado HTTP 404 (Not Found).
     */
    @PutMapping("/{productId}")
    public ResponseEntity<?> editProduct(@PathVariable Long productId, @RequestBody ProductDto productDto) {
        try {
            ProductDto updatedProduct = productService.editProduct(productId, productDto);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
