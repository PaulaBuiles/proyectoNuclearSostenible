package co.edu.cue.proyectoNuclearSostenible.infraestructure.controller;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.ProductCategory;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.UserDto;
import co.edu.cue.proyectoNuclearSostenible.service.ProductService;
import co.edu.cue.proyectoNuclearSostenible.service.UserService;
import jdk.jfr.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product_category")
@Controller
public class ProductCategoryController {

    @Autowired
    private ProductService productService;

    @PostMapping(headers = "Accept=application/json")
    public ResponseEntity<?> createCategory (@RequestBody ProductCategory category) {
        try {
            return new ResponseEntity<>(productService.createCategory(category), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
}
