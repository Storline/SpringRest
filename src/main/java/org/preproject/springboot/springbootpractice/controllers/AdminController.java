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
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/new")
    public String newUserForm(Model model){
        model.addAttribute("newuser", new User());
        return "redirect:/admin";
    }

    @PostMapping("/new")
    public String addUser(User user){
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("")
    public String listUsers(Model model, Principal principal){
        Optional<User> username = userService.findByUsername(principal.getName());
        model.addAttribute("adminuser", username);
        model.addAttribute("adminusers", userService.getAllUsers());
        model.addAttribute("newUser", new User());
        List<Role> roles = userService.getRoleList();
        model.addAttribute("allRoles", roles);
        return "adminpage";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam (value = "id") Long id){
        model.addAttribute("user", userService.getUserById(id));
        return "redirect:/admin";
    }

    @PostMapping("/edit")
    public String updateUser(@ModelAttribute("user") User user){
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}")
    public String getUserById(@RequestParam("id") Long id, Model model) {
        model.addAttribute(userService.getUserById(id));
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String removeUser(@RequestParam (value = "id") Long id){
        userService.removeUserById(id);
        return "redirect:/admin";
    }
}
