package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.service.UserServiceInterface;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;


import java.util.List;


@Controller
public class UserController {

    private final UserServiceInterface userServiceInterface;

    public UserController(UserServiceInterface userServiceInterface) {
        this.userServiceInterface = userServiceInterface;
    }

    @GetMapping("/admin")
    public String showAllUsers(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("users", userServiceInterface.allUsers());
        model.addAttribute("listRoles", userServiceInterface.findRoles());
        model.addAttribute("userInfo", (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "admin";
    }

    @PostMapping(value = "/admin/new")
    public String addUser(@ModelAttribute("user") User user) {
        if (userServiceInterface.check(user)) {
            userServiceInterface.saveUser(user);
            return "redirect:/admin";

        } else {
            return "/error";
        }
    }
    @DeleteMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        userServiceInterface.deleteUser(id);
        return "redirect:/admin";
    }

    @PatchMapping("/admin/edit/{id}")
    public String updateUser(@ModelAttribute("user") User user, BindingResult result, @PathVariable("id") Integer id) {
        userServiceInterface.updateUser(id, user);
        return "redirect:/admin";
    }

    @GetMapping("/user")
    public String findUser(ModelMap model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);
        return "user";
    }
}




