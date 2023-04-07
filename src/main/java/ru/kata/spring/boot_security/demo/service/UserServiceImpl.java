package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;


import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserDetailsService, UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    @Override
    public User findUserById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(new User());
    }

    @Override
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public boolean check(User user) {
        if (userRepository.findByUsername(user.getUsername()) == null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public User findByUsername(String email) {
        return userRepository.findByUsername(email);
    }

    @Transactional
    public boolean deleteUser(int id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Override
    public List<Role> findRoles() {
        return roleRepository.findAll();
    }

    @Transactional
    public void updateUser(Integer id, User user) {
        user.setId(id);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}

