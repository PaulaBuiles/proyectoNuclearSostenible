package co.edu.cue.proyectoNuclearSostenible.service.imp;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.ProductCategory;
import co.edu.cue.proyectoNuclearSostenible.domain.entities.State;
import co.edu.cue.proyectoNuclearSostenible.domain.entities.TypeId;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.StateDao;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.TypeIdDao;
import co.edu.cue.proyectoNuclearSostenible.service.StateService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StateServiceImpl implements StateService {

    @Autowired
    private StateDao stateDao;

    public State createState(State state) {
        String lowercaseTitle = state.getDescription().toLowerCase();
        State existingCategory = stateDao.findByDescriptionIgnoreCase(lowercaseTitle);
        if(existingCategory != null) {
            throw new IllegalArgumentException("Ya existe una estado con el nombre proporcionado.");
        }
        return stateDao.save(state);
    }

    public State getById(Long id){
        return stateDao.findStateById(id);
    }

}
