package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    User findUserById(Integer id);
    List<User> allUsers();
    void saveUser(User user);
    boolean deleteUser(int id);
    void addUser(User user);
    List<Role> findRoles();
    void updateUser(Integer id, User user);
    boolean check(User user);

    User findByUsername(String email);
}

