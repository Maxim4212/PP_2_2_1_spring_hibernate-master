package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserDaoImp(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM User u JOIN FETCH u.car", User.class)
                .getResultList();
    }

    @Override
    public User getUserByCar(String model, int series) {
        String hql = "FROM User u JOIN FETCH u.car WHERE u.car.model = :model AND u.car.series = :series";
        return sessionFactory.getCurrentSession()
                .createQuery(hql, User.class)
                .setParameter("model", model)
                .setParameter("series", series)
                .uniqueResult();
    }

    @Override
    public void cleanUsersTable() {
        sessionFactory.getCurrentSession().createQuery("DELETE FROM User").executeUpdate();
    }

    @Override
    public void cleanCarsTable() {
        sessionFactory.getCurrentSession().createQuery("DELETE FROM Car").executeUpdate();
    }
}
