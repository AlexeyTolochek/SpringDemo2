package ru.java.mentor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.java.mentor.factory.DAO;
import ru.java.mentor.model.User;
import java.util.List;

@Service
public class UserService {
    private DAO dao;

    @Autowired
    public void setDao(DAO dao){
        this.dao = dao;
    }

    
    @Transactional
    public List<User> getAllUsers()  {
        return dao.getAllUsers();
    }

    
    @Transactional
    public User getUserById(Long id) {
        return dao.getUserById(id);
    }

    
    @Transactional
    public User getUserByLogin(String login) {
        return dao.getUserByLogin(login);
    }

    
    @Transactional
    public void addUser(User user) {
        dao.addUser(user);
    }

    
    @Transactional
    public void editUser(User user) {
        dao.editUser(user);
    }

    
    @Transactional
    public void deleteUser(User user) {
        dao.deleteUser(user);
    }

    
    @Transactional
    public void dropTable() {
        dao.dropTable();
    }

    
    @Transactional
    public void createTable()  {
    }

    
    @Transactional
    public boolean validateUser(String login, String password) {
        try {
            return dao.validateUser(login, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    
    @Transactional
    public boolean isExistLogin(String login) {
        try {
            return dao.isExistLogin(login);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
