package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserServiceInterface  {
    public User findUserById(Integer id);
    public List<User> allUsers();
    public void saveUser(User user);
    public boolean deleteUser(int id);
    public void addUser(User user);
    public List<Role> findRoles();
    public void updateUser(Integer id, User user);
    public boolean check(User user);

    User findByUsername(String email);
}

