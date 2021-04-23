package org.preproject.springboot.springbootpractice.controllers;

import org.preproject.springboot.springbootpractice.model.Role;
import org.preproject.springboot.springbootpractice.model.User;
import org.preproject.springboot.springbootpractice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public String showAllUsers(Model model, Principal principal) {
        Optional<User> user = userService.findByUsername(principal.getName());
        model.addAttribute("adminuser", user);
        model.addAttribute("adminusers", userService.getAllUsers());
        model.addAttribute("newUser", new User());
        List<Role> roles = userService.getRoleList();
        model.addAttribute("allRole", roles);
        return "adminpage";
    }

    @GetMapping("/new")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "redirect:/admin";
    }

    @PostMapping("/new")
    public String add(@ModelAttribute("user") User user,
                      @RequestParam(value = "rolesArray") Long[] role) {
        Set<Role> roleSet = new HashSet<>();
        for (Long roles : role){
            roleSet.add(userService.getRoleById(roles));
        }
        user.setRoles(roleSet);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit")
    public String editUser(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles", userService.getRoleList());
        return "redirect:/admin";
    }

    @PostMapping("/edit")
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam(value = "rolesArray") Long[] role) {
        Set<Role> roleSet = new HashSet<>();
        for (Long roles : role){
            roleSet.add(userService.getRoleById(roles));
        }
        user.setRoles(roleSet);
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam (value = "id") Long id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }

    @GetMapping("/{id}")
    public String showUser(Principal principal, Model model){
        Optional<User> username = userService.findByUsername(principal.getName());
        model.addAttribute("user", username);
        return "redirect:/admin";
    }
}
