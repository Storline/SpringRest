package org.preproject.springboot.springbootpractice.controllers;

import org.preproject.springboot.springbootpractice.model.Role;
import org.preproject.springboot.springbootpractice.model.User;
import org.preproject.springboot.springbootpractice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("admin")
public class AdminController {

    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showList(Model model, Principal principal){
        Optional<User> user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("newUser", new User());
        model.addAttribute("users", userService.getAllUsers());
        List<Role> roles = userService.getRoleList();
        model.addAttribute("allRoles", roles);
        return "adminpage";
    }
}
