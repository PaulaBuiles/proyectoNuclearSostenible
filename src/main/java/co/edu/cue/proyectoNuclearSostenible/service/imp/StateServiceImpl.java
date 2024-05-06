package co.edu.cue.proyectoNuclearSostenible.service.imp;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.State;
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
        return stateDao.save(state);
    }
}
