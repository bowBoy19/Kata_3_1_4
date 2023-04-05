package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userServiceInterface) {
        this.userService = userServiceInterface;
    }

    @GetMapping("/admin")
    public String showAllUsers(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("users", userService.allUsers());
        model.addAttribute("listRoles", userService.findRoles());
        model.addAttribute("userInfo", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "admin";
    }

    @PostMapping(value = "/admin/new")
    public String addUser(@ModelAttribute("user") User user) {
        if (userService.check(user)) {
            userService.saveUser(user);
            return "redirect:/admin";

        } else {
            return "/error";
        }
    }
    @DeleteMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @PatchMapping("/admin/edit/{id}")
    public String updateUser(@ModelAttribute("user") User user, BindingResult result, @PathVariable("id") Integer id) {
        userService.updateUser(id, user);
        return "redirect:/admin";
    }
}
