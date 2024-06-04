package co.edu.cue.proyectoNuclearSostenible.service;


import co.edu.cue.proyectoNuclearSostenible.domain.entities.Product;
import co.edu.cue.proyectoNuclearSostenible.domain.entities.ProductCategory;
import co.edu.cue.proyectoNuclearSostenible.domain.entities.Publication;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.ProductDto;

import java.util.List;

public interface ProductService {
    ProductCategory createCategory(ProductCategory category);

    ProductDto createProduct(ProductDto productDto);


    Product getById(Long id);

    List<Product> getAllProducts();

    ProductCategory getCategoryById(Long id);

    List<Product> getProductByUserId(Long id);

    Publication getLastPublication(Long productId);

    ProductDto editProduct(Long productId, ProductDto productDto);
}
