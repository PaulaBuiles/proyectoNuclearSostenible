package co.edu.cue.proyectoNuclearSostenible.service.imp;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.ProductCategory;
import co.edu.cue.proyectoNuclearSostenible.domain.entities.TypeId;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.TypeIdDao;
import co.edu.cue.proyectoNuclearSostenible.service.TypeIdService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.StyledEditorKit;
import java.util.List;

@Service
@AllArgsConstructor
public class TypeIdServiceImpl implements TypeIdService {
    @Autowired
    private TypeIdDao typeIdDao;
    /**
     * Obtiene un tipo de identificación a partir de su ID.
     *
     * @param id La ID del tipo de identificación a obtener.
     * @return El tipo de identificación correspondiente a la ID proporcionada.
     */
    public TypeId getById(Long id){
        return typeIdDao.findTypeIdById(id);
    }

    /**
     * Crea un nuevo tipo de identificación en el sistema.
     *
     * @param typeId El tipo de identificación a crear.
     * @return El tipo de identificación creado.
     */
    public TypeId createType(TypeId typeId) {
        return typeIdDao.save(typeId);
    }

    /**
     * Valida la información de un tipo de identificación antes de crearlo.
     *
     * @param typeId El tipo de identificación a validar.
     * @return El tipo de identificación validado y creado.
     * @throws IllegalArgumentException Si se encuentran múltiples tipos de identificación con el mismo código o
     * descripción, o si ya existe un tipo de identificación con el mismo código y descripción, código o descripción.
     */
    public TypeId validateInfo(TypeId typeId) {
        // Convertir el código y la descripción a minúsculas para la comparación insensible a mayúsculas y minúsculas
        String lowercaseCode = typeId.getCode().toLowerCase();
        String lowercaseDescription = typeId.getDescription().toLowerCase();

        // Buscar tipos de identificación existentes con el mismo código o descripción
        List<TypeId> existingTypes = typeIdDao.findByCodeIgnoreCaseOrDescriptionIgnoreCase(lowercaseCode, lowercaseDescription);

        // Verificar si se encontraron múltiples tipos de identificación con el mismo código o descripción
        if (existingTypes.size() > 1) {
            throw new IllegalArgumentException("Se encontraron múltiples tipos de identificación con el mismo código o descripción.");
        }

        // Verificar si ya existe un tipo de identificación con el mismo código y descripción, código o descripción
        if (!existingTypes.isEmpty()) {
            TypeId existingType = existingTypes.get(0);
            if (existingType.getCode().equalsIgnoreCase(lowercaseCode) && existingType.getDescription().equalsIgnoreCase(lowercaseDescription)) {
                throw new IllegalArgumentException("Ya existe un tipo de identificación con ese código y descripción.");
            } else if (existingType.getCode().equalsIgnoreCase(lowercaseCode)) {
                throw new IllegalArgumentException("Ya existe un tipo de identificación con ese código.");
            } else if (existingType.getDescription().equalsIgnoreCase(lowercaseDescription)) {
                throw new IllegalArgumentException("Ya existe un tipo de identificación con esa descripción.");
            }
        }

        // Crear y devolver el tipo de identificación validado
        return createType(typeId);
    }
}
