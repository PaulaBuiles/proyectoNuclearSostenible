package co.edu.cue.proyectoNuclearSostenible.service.imp;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Product;
import co.edu.cue.proyectoNuclearSostenible.domain.entities.ProductCategory;
import co.edu.cue.proyectoNuclearSostenible.domain.entities.State;
import co.edu.cue.proyectoNuclearSostenible.domain.entities.User;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.ProductCategoryDao;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.ProductDao;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.ProductDto;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.UserDto;
import co.edu.cue.proyectoNuclearSostenible.mapping.mapper.ProductMapper;
import co.edu.cue.proyectoNuclearSostenible.mapping.mapper.UserMapper;
import co.edu.cue.proyectoNuclearSostenible.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImp implements ProductService {

    @Qualifier("productMapper")
    private ProductMapper mapper;

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Autowired
    private ProductDao productDao;

    private UserServiceImp userService;

    public ProductCategory createCategory(ProductCategory category) {
        String lowercaseTitle = category.getTitle().toLowerCase();
        ProductCategory existingCategory = productCategoryDao.findByTitleIgnoreCase(lowercaseTitle);
        if(existingCategory != null) {
            throw new IllegalArgumentException("Ya existe una categoría con el título proporcionado.");
        }
        return productCategoryDao.save(category);
    }

    public ProductDto createProduct(ProductDto productDto) {
        validateProductInfo(productDto);
        Product product = mapper.mapToEntity(productDto);
        product.setUser(userService.getById(productDto.userId()));
        return mapper.mapToDTO(productDao.save(product));
    }

    private void validateProductInfo(ProductDto productDto) {
        String name = productDto.name().toLowerCase();
        Long userId = productDto.userId();

        List<Product> existingProducts = productDao.findByNameIgnoreCaseOrUser_IdUser(name,userId);

        if (!existingProducts.isEmpty()) {
            throw new IllegalArgumentException("Ya existe un producto con ese nombre.");
        }
    }

    public Product getProduct(ProductDto product) {
        return productDao.findById(product.idProduct()).get();
    }

    public Product getById(Long id){
        return productDao.findProductById(id);
    }

}
