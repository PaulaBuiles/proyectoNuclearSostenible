package co.edu.cue.proyectoNuclearSostenible.service.imp;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.TypeId;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.TypeIdDao;
import co.edu.cue.proyectoNuclearSostenible.service.TypeIdService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
