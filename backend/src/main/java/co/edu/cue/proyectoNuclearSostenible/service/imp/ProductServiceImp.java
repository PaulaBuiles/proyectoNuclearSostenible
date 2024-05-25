package co.edu.cue.proyectoNuclearSostenible.service.imp;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Product;
import co.edu.cue.proyectoNuclearSostenible.domain.entities.ProductCategory;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.ProductCategoryDao;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.ProductDao;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.ProductDto;
import co.edu.cue.proyectoNuclearSostenible.mapping.mapper.ProductMapper;
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


    /**
     * Crea una nueva categoría de producto en el sistema.
     *
     * @param category La categoría de producto a crear.
     * @return La categoría de producto creada.
     * @throws IllegalArgumentException Si ya existe una categoría con el mismo título proporcionado.
     */
    public ProductCategory createCategory(ProductCategory category) {

        String lowercaseTitle = category.getTitle().toLowerCase();

        ProductCategory existingCategory = productCategoryDao.findByTitleIgnoreCase(lowercaseTitle);
        if(existingCategory != null) {
            throw new IllegalArgumentException("Ya existe una categoría con el título proporcionado.");
        }
        return productCategoryDao.save(category);
    }

    /**
     * Obtiene una categoria a partir de su ID.
     *
     * @param id El ID del producto a obtener.
     * @return El producto correspondiente al ID proporcionado.
     */
    public ProductCategory getCategoryById(Long id){return productCategoryDao.findProductCategoryById(id);}

    public List<Product> getProductByUserId(Long id) {
        return productDao.findByUser_IdUser(id);
    }

    /**
     * Crea un nuevo producto en el sistema.
     *
     * @param productDto Los datos del producto a crear.
     * @return El DTO del producto creado.
     * @throws IllegalArgumentException Si ya existe un producto con el mismo nombre.
     */
    public ProductDto createProduct(ProductDto productDto) {
        validateProductInfo(productDto);

        Product product = mapper.mapToEntity(productDto);

        product.setProductCategory(getCategoryById(productDto.categoryId()));

        product.setUser(userService.getById(productDto.userId()));

        return mapper.mapToDTO(productDao.save(product));
    }

    /**
     * Valida la información del producto antes de crearlo.
     *
     * @param productDto Los datos del producto a validar.
     * @throws IllegalArgumentException Sí ya existe un producto con el mismo nombre.
     */
    private void validateProductInfo(ProductDto productDto) {
        String name = productDto.name().toLowerCase();

        Long userId = productDto.userId();

        List<Product> existingProducts = productDao.findByNameIgnoreCaseAndUser_IdUser(name,userId);

        if (!existingProducts.isEmpty()) {
            throw new IllegalArgumentException("Ya existe un producto con ese nombre.");
        }
    }

    /**
     * Obtiene un producto a partir de su DTO.
     *
     * @param product El DTO del producto.
     * @return El producto correspondiente al DTO proporcionado.
     */
    public Product getProduct(ProductDto product) {
        return productDao.findById(product.idProduct()).get();
    }

    /**
     * Obtiene un producto a partir de su ID.
     *
     * @param id El ID del producto a obtener.
     * @return El producto correspondiente al ID proporcionado.
     */
    public Product getById(Long id){
        return productDao.findProductById(id);
    }




    /**
     * Obtiene una lista de productos.
     */
    public List<Product> getAllProducts() {
        return productDao.findAll();
    }

}
