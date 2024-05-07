package co.edu.cue.proyectoNuclearSostenible.service.imp;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.ProductCategory;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.ProductCategoryDao;
import co.edu.cue.proyectoNuclearSostenible.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImp implements ProductService {
    @Autowired
    private ProductCategoryDao productCategoryDao;

    public ProductCategory createCategory(ProductCategory category) {
        String lowercaseTitle = category.getTitle().toLowerCase();
        ProductCategory existingCategory = productCategoryDao.findByTitleIgnoreCase(lowercaseTitle);
        if(existingCategory != null) {
            throw new IllegalArgumentException("Ya existe una categoría con el título proporcionado.");
        }
        return productCategoryDao.save(category);
    }

}
