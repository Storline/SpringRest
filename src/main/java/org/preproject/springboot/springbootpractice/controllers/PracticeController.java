package org.preproject.springboot.springbootpractice.controllers;

import org.preproject.springboot.springbootpractice.model.User;
import org.preproject.springboot.springbootpractice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
public class PracticeController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/practice")
    public String practice(Model model, Principal principal){
        Optional<User> username = userService.findByUsername(principal.getName());
        model.addAttribute("users", username);
        return "practice_all";
    }

//    @GetMapping("/practice_all")
//    public String listUsers(Model model){
//        model.addAttribute("practieceusers", userService.getAllUsers());
//        return "practice_template";
//    }

}
