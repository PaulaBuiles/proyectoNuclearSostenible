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
    public TypeId getById(Long id){
        return typeIdDao.findTypeIdById(id);
    }

    public TypeId createType(TypeId typeId) {
        return typeIdDao.save(typeId);
    }

    public TypeId validateInfo(TypeId typeId) {
        String lowercaseCode = typeId.getCode().toLowerCase();
        String lowercaseDescription = typeId.getDescription().toLowerCase();
        List<TypeId> existingTypes = typeIdDao.findByCodeIgnoreCaseOrDescriptionIgnoreCase(lowercaseCode, lowercaseDescription);
        if (existingTypes.size() > 1) {
            throw new IllegalArgumentException("Se encontraron múltiples tipos de identificación con el mismo código o descripción.");
        }
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
        return createType(typeId);
    }


}
