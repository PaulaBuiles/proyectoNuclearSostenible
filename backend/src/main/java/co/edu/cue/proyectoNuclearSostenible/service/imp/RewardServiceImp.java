package co.edu.cue.proyectoNuclearSostenible.service.imp;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Reward;
import co.edu.cue.proyectoNuclearSostenible.domain.entities.User;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.RewardDao;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.UserDao;
import co.edu.cue.proyectoNuclearSostenible.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RewardServiceImp implements RewardService {

    @Autowired
    private RewardDao rewardDao;

    @Autowired
    private UserDao userDao;


    @Override
    public void addPoints(User user, int points, String description) {
        Reward reward = new Reward();
        reward.setDescription(description);
        reward.setPoints_value(points);
        reward.getUsers().add(user);
        user.getRewards().add(reward);

        rewardDao.save(reward);
        userDao.save(user);
    }

    @Override
    public void redeemPoints(User user, int points, String rewardDescription) {
        Integer totalPoints = rewardDao.findTotalPointsByUserId(user.getIdUser());
        if (totalPoints != null && totalPoints >= points) {
            Reward reward = new Reward();
            reward.setDescription(rewardDescription);
            reward.setPoints_value(-points);
            reward.getUsers().add(user);
            user.getRewards().add(reward);

            rewardDao.save(reward);
            userDao.save(user);
        } else {
            throw new IllegalArgumentException("Insufficient points");
        }
    }

    @Override
    public int getPoints(User user) {
        Integer totalPoints = rewardDao.findTotalPointsByUserId(user.getIdUser());
        return totalPoints != null ? totalPoints : 0;
    }

}
