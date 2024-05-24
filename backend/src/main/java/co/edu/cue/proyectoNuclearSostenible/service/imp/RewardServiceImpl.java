package co.edu.cue.proyectoNuclearSostenible.service.imp;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Reward;
import co.edu.cue.proyectoNuclearSostenible.domain.entities.User;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.RewardDao;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.UserDao;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.RewardDto;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.UserDto;
import co.edu.cue.proyectoNuclearSostenible.mapping.mapper.RewardMapper;
import co.edu.cue.proyectoNuclearSostenible.mapping.mapper.UserMapper;
import co.edu.cue.proyectoNuclearSostenible.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RewardServiceImpl implements RewardService {

    @Autowired
    private RewardDao rewardDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RewardMapper rewardMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void addPoints(UserDto userDto, int points, String description) {

        User user = userMapper.mapToEntity(userDto);

        Reward reward = new Reward();
        reward.setDescription(description);
        reward.setPoints_value(points);
        reward.getUserRewards().add(user);
        user.getRewards().add(reward);

        user.setPoints(user.getPoints() + points);

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
            user.setPoints(user.getPoints() - points);
            userDao.save(user);
        } else {
            throw new IllegalArgumentException("Insufficient points");
        }
    }

    /**
     * Obtiene los puntos de un usuario.
     *
     * @param userDto DTO del usuario.
     * @return La cantidad de puntos del usuario.
     */
    @Override
    public int getPoints(UserDto userDto) {
        User user = userMapper.mapToEntity(userDto);
        return user.getPoints();
    }

}
