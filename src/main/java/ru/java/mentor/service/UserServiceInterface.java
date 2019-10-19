package ru.java.mentor.service;

import ru.java.mentor.model.User;
import java.util.List;

public interface UserServiceInterface {

    public List<User> getAllUsers();

    public User getUserById(Long id);

    public User getUserByLogin(String login);

    public User editUserView(String edit, User user);

    public void deleteUser(User user);

    public void dropTable();

    public void createTable();

    public boolean updateUser(User user);

    public boolean addUser(User user);

    public boolean validateUser(User user);

    public boolean isExistLogin(String login);

    public boolean notNullDataLoginPassword(User user);

    public boolean notNullDataUser(User  user);

    public boolean notNullDataAdmin(User  user);
}
