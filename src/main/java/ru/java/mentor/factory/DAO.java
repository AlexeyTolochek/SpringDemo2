package ru.java.mentor.factory;

import ru.java.mentor.model.User;
import java.util.List;

public interface DAO {

    List<User> getAllUsers();

    User getUserById(Long id);

    User getUserByLogin(String login);

    void addUser(User user);

    void editUser(User user);

    void deleteUser(User user);

    void dropTable();

    void createTable();

    boolean validateUser(String login, String password);

    boolean isExistLogin(String login);
}
