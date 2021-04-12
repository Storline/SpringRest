package org.preproject.springboot.springbootpractice.controllers;

import org.preproject.springboot.springbootpractice.model.User;
import org.preproject.springboot.springbootpractice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        return "adminadduser";
    }

    @PostMapping("")
    public String addUser(User user/*, @RequestParam(value = "role") String... roles*/){
//        user.getRoles().add(new Role(roles));

        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("")
    public String listUsers(Model model){
        model.addAttribute("adminusers", userService.getAllUsers());
        return "adminpage";
    }

    @GetMapping("/user/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id){
        model.addAttribute("user", userService.getUserById(id));
        return "adminedituser";
    }

    @PostMapping("/user/{id}/edit")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") Long id){
        userService.updateUser(id, user);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/user/{id}", method = {RequestMethod.GET, RequestMethod.PUT})
    public String getUserById(@PathVariable("id") Long id, Model model) {
        model.addAttribute(userService.getUserById(id));
        return "admingetuser";
    }

    @PostMapping("/user/{id}/delete")
    public String removeUser(@PathVariable("id") Long id){
        userService.removeUserById(id);
        return "redirect:/admin";
    }
}
