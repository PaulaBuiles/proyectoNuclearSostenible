package co.edu.cue.proyectoNuclearSostenible.service;


import co.edu.cue.proyectoNuclearSostenible.domain.entities.Product;
import co.edu.cue.proyectoNuclearSostenible.domain.entities.ProductCategory;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.ProductDto;

public interface ProductService {
    ProductCategory createCategory(ProductCategory category);

    ProductDto createProduct(ProductDto productDto);

    Product getProduct(ProductDto product);

    Product getById(Long id);

}
