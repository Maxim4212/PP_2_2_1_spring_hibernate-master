package hiber.service;

import hiber.dao.UserDao;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

   @Autowired
   private SessionFactory sessionFactory;

   @Autowired
   private UserDao userDao;

   @Transactional
   @Override
   public void add(User user) {
      userDao.add(user);
   }

   @Transactional(readOnly = true)
   @Override
   public List<User> listUsers() {
      return userDao.listUsers();
   }

   public User getUserByCar(String model, int series) {
      String hql = "FROM User u WHERE u.car.model = :model AND u.car.series = :series";

      return sessionFactory.getCurrentSession()
              .createQuery(hql, User.class)
              .setParameter("model", model)
              .setParameter("series", series)
              .uniqueResult();
   }
}
