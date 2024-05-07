package co.edu.cue.proyectoNuclearSostenible.service.imp;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.State;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.StateDao;
import co.edu.cue.proyectoNuclearSostenible.service.StateService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StateServiceImpl implements StateService {

    @Autowired
    private StateDao stateDao;

    /**
     * Crea un nuevo estado en el sistema.
     *
     * @param state El estado a crear.
     * @return El estado creado.
     * @throws IllegalArgumentException Si ya existe un estado con el mismo nombre proporcionado.
     */
    public State createState(State state) {
        // Convertir el título a minúsculas para la comparación insensible a mayúsculas y minúsculas
        String lowercaseTitle = state.getDescription().toLowerCase();

        // Verificar si ya existe un estado con el mismo nombre proporcionado
        State existingCategory = stateDao.findByDescriptionIgnoreCase(lowercaseTitle);
        if(existingCategory != null) {
            throw new IllegalArgumentException("Ya existe un estado con el nombre proporcionado.");
        }

        // Guardar y devolver el nuevo estado
        return stateDao.save(state);
    }

    /**
     * Obtiene un estado a partir de su ID.
     *
     * @param id El ID del estado a obtener.
     * @return El estado correspondiente al ID proporcionado.
     */
    public State getById(Long id){
        return stateDao.findStateById(id);
    }
}
