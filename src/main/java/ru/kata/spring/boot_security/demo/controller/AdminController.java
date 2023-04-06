package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userServiceInterface) {
        this.userService = userServiceInterface;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showAllUsers(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("users", userService.allUsers());
        model.addAttribute("listRoles", userService.findRoles());
        model.addAttribute("userInfo", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "admin";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String addUser(@ModelAttribute("user") User user) {
        if (userService.check(user)) {
            userService.saveUser(user);
            return "redirect:/admin";
        } else {
            return "/error";
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable("id") Integer id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.PATCH)
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") Integer id) {
        userService.updateUser(id, user);
        return "redirect:/admin";
    }
}
