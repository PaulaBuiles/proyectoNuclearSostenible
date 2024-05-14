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
        // Convertir el título a minúsculas para la comparación insensible a mayúsculas y minúsculas
        String lowercaseTitle = category.getTitle().toLowerCase();

        // Verificar si ya existe una categoría con el mismo título proporcionado
        ProductCategory existingCategory = productCategoryDao.findByTitleIgnoreCase(lowercaseTitle);
        if(existingCategory != null) {
            throw new IllegalArgumentException("Ya existe una categoría con el título proporcionado.");
        }

        // Guardar y devolver la nueva categoría de producto
        return productCategoryDao.save(category);
    }

    /**
     * Crea un nuevo producto en el sistema.
     *
     * @param productDto Los datos del producto a crear.
     * @return El DTO del producto creado.
     * @throws IllegalArgumentException Si ya existe un producto con el mismo nombre.
     */
    public ProductDto createProduct(ProductDto productDto) {
        // Validar la información del producto
        validateProductInfo(productDto);

        // Mapear DTO a entidad
        Product product = mapper.mapToEntity(productDto);

        // Obtener y configurar el usuario propietario del producto
        product.setUser(userService.getById(productDto.userId()));

        // Guardar el producto en la base de datos y mapear el resultado a un DTO
        return mapper.mapToDTO(productDao.save(product));
    }

    /**
     * Valida la información del producto antes de crearlo.
     *
     * @param productDto Los datos del producto a validar.
     * @throws IllegalArgumentException Sí ya existe un producto con el mismo nombre.
     */
    private void validateProductInfo(ProductDto productDto) {
        // Convertir el nombre a minúsculas para la comparación insensible a mayúsculas y minúsculas
        String name = productDto.name().toLowerCase();

        // Obtener la ID del usuario propietario
        Long userId = productDto.userId();

        // Buscar productos existentes con el mismo nombre y usuario propietario
        List<Product> existingProducts = productDao.findByNameIgnoreCaseOrUser_IdUser(name, userId);

        // Verificar si ya existe un producto con el mismo nombre
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

}
