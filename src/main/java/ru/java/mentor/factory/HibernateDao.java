package ru.java.mentor.factory;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.java.mentor.model.User;
import java.util.List;

@Repository
public class HibernateDao implements DAO {
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from User").list();
    }

    @Override
    public void addUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.save(user);
    }

    @Override
    public void editUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.update(user);
    }

    @Override
    public void deleteUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(user);
    }

    @Override
    public void dropTable() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete from User");
        query.executeUpdate();
    }

    @Override
    public void createTable() {
    }

    @Override
    public boolean validateUser(String login, String password) {
        User user = getUserByLogin(login);
        if (user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isExistLogin(String login) {
        User user = getUserByLogin(login);
        if (user!=null) {
            return true;
        }
        return false;
    }

    @Override
    public User getUserById(Long id) {
        Session session = sessionFactory.getCurrentSession();
      /*  Query query = session.createQuery("from User where id=:id")
                .setParameter("id", id);
        User user = (User) query.uniqueResult();*/
        return session.get(User.class, id);
    }

    public User getUserByLogin(String login) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from User where login=:login")
                .setParameter("login", login);
        User user = (User) query.uniqueResult();
        return user;
    }
}