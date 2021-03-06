package org.preproject.springboot.springbootpractice.controllers;

import org.preproject.springboot.springbootpractice.model.User;
import org.preproject.springboot.springbootpractice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest")
public class RESTController {

    private UserService userService;

    @Autowired
    public RESTController (UserService userService){
        this.userService = userService;
    }

    @GetMapping(value = "/all_users")
    public ResponseEntity<List<User>> allUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @GetMapping(value ="/infoUser")
    @ResponseBody
    public Optional<User> infoUser(Principal principal){
        return userService.findByUsername(principal.getName());
    }

    @GetMapping(value = "/findUser/{id}")
    public User findUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping(value = "/edit/{id}")
    public ResponseEntity<?> editUser(@RequestBody User user, @PathVariable Long id) {
        userService.updateUser(user, id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/addUser", produces = "application/json")
    private ResponseEntity<?> addUser(@RequestBody User user) {
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        userService.removeUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
