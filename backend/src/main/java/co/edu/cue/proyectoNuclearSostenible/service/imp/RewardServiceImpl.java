package co.edu.cue.proyectoNuclearSostenible.service.imp;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Reward;
import co.edu.cue.proyectoNuclearSostenible.domain.entities.User;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.RewardDao;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.UserDao;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.UserDto;
import co.edu.cue.proyectoNuclearSostenible.mapping.mapper.UserMapper;
import co.edu.cue.proyectoNuclearSostenible.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RewardServiceImpl implements RewardService {

    @Autowired
    private RewardDao rewardDao;

    @Autowired
    private UserDao userDao;


    @Autowired
    private UserMapper userMapper;

    /**
     * Añade puntos a un usuario y crea una recompensa asociada.
     *
     * Este método actualiza los puntos de un usuario existente y crea una nueva recompensa
     * que se asocia al usuario. La recompensa también se guarda en la base de datos.
     *
     * @param userDto El DTO del usuario que recibirá los puntos.
     * @param points La cantidad de puntos a añadir al usuario.
     * @param description La descripción de la recompensa asociada.
     * @throws IllegalArgumentException Si no se encuentra el usuario con el ID proporcionado en el DTO.
     */
    @Override
    public void addPoints(UserDto userDto, int points, String description) {
        User user = userDao.findById(userDto.idUser())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con el ID: " + userDto.idUser()));

        Reward reward = new Reward();
        reward.setDescription(description);
        reward.setPoints_value(points);
        reward.setUserRewards(new ArrayList<>());
        reward.getUserRewards().add(user);

        if (user.getRewards() == null) {
            user.setRewards(new ArrayList<>());
        }
        user.getRewards().add(reward);

        user.updatePoints(points);

        rewardDao.save(reward);
        userDao.save(user);
    }


    /**
     * Redime puntos de un usuario.
     *
     * @param userDto DTO del usuario que va a redimir puntos.
     * @param points  Cantidad de puntos a redimir.
     * @throws IllegalArgumentException si el usuario no tiene suficientes puntos.
     */
    @Override
    public void redeemPoints(UserDto userDto, int points) {

        User user = userMapper.mapToEntity(userDto);

        if (user.getPoints() >= points) {
            user.updatePoints(-points);
            userDao.save(user);
        } else {
            throw new IllegalArgumentException("Puntos Insuficientes");
        }
    }

    public List<Reward> getRewardsByUserId(Long userId) {
        return rewardDao.findRewardsByUserId(userId);
    }

    /**
     * Obtiene el total de puntos de recompensa acumulados por un usuario específico.
     *
     * @param userId el ID del usuario cuyos puntos se desean consultar.
     * @return el total de puntos acumulados por el usuario.
     */
    public Integer getTotalPointsByUserId(Long userId) {
        return rewardDao.findTotalPointsByUserId(userId);
    }


}
