package ru.java.mentor.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "login", unique = true)
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "address")
    private String address;

    @Column(name = "role")
    private String role;

    public User() {
        this.role = "User";
    }

    public User(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.role= "User";
    }

    public User(String name, String login, String password, String address) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.address = address;
        this.role= "User";
    }

    public User(String name, String login, String password, String address, String role) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.address = address;
        this.role = role;
    }

    public User(long id, String name, String login, String password, String address) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.address = address;
        this.role= "User";
    }

    public User(long id, String name, String login, String password, String address, String role) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.address = address;
        this.role= role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        if (role.equals("Administrator") || role.equals("User")) {
            this.role = role;
        } else {
            throw new IllegalArgumentException("Incorrect user role");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return " Имя пользователя: " + name +
                " id= " + id +
                " login= " + login +
                " password= " + password +
                " address= " + address +
                " role= " + role;
    }
}
