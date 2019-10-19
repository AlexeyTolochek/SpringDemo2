package ru.java.mentor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.java.mentor.dao.UserDao;
import ru.java.mentor.model.User;
import java.util.List;

@Service
public class UserService implements UserServiceInterface {
    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao){
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public List<User> getAllUsers()  {
        return userDao.getAllUsers();
    }
    
    @Override
    @Transactional
    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }

    @Override
    @Transactional
    public User getUserByLogin(String login) {
        return userDao.getUserByLogin(login);
    }

    @Override
    @Transactional
    public boolean addUser(User user) {
        if (notNullDataUser(user) && !isExistLogin(user.getLogin())) {
            userDao.addUser(user);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean updateUser(User user) {
        if (notNullDataUser(user)) {
            userDao.editUser(user);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void deleteUser(User user) {
        userDao.deleteUser(user);
    }

    @Override
    @Transactional
    public void dropTable() {
        userDao.dropTable();
    }
    
    @Override
    @Transactional
    public void createTable()  {
    }
    
    @Override
    @Transactional
    public boolean validateUser(User user) {
        if (notNullDataLoginPassword(user)) {
            try {
                return userDao.validateUser(user.getLogin(), user.getPassword());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    @Transactional
    public boolean isExistLogin(String login) {
        try {
            return userDao.isExistLogin(login);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    @Transactional
    public boolean notNullDataLoginPassword(User user) {
        return user.getLogin() != null && !user.getLogin().isEmpty() &&
                user.getPassword() != null && !user.getPassword().isEmpty();
    }

    @Override
    @Transactional
    public boolean notNullDataUser(User user) {
        return user.getLogin() != null && !user.getLogin().isEmpty() &&
                user.getName() != null && !user.getName().isEmpty() &&
                user.getPassword() != null && !user.getPassword().isEmpty() &&
                user.getAddress() != null && !user.getAddress().isEmpty();

    }

    @Override
    @Transactional
    public boolean notNullDataAdmin(User user) {
        return user.getLogin() != null && !user.getLogin().isEmpty() &&
                user.getName() != null && !user.getName().isEmpty() &&
                user.getPassword() != null && !user.getPassword().isEmpty() &&
                user.getAddress() != null && !user.getAddress().isEmpty() &&
                user.getRole() != null && !user.getRole().isEmpty() &&
                (user.getRole().equals("Administrator") || user.getRole().equals("User"));
    }

    @Override
    @Transactional
    public User editUserView(String edit, User user) {
        User editUser = null;
        if (!edit.isEmpty()) {
            editUser = getUserById(user.getId());
        }
        return editUser;
    }
}
