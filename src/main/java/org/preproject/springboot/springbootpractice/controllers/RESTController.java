package org.preproject.springboot.springbootpractice.controllers;

import org.preproject.springboot.springbootpractice.model.User;
import org.preproject.springboot.springbootpractice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value ="/user/{id}")
    public ResponseEntity<User> showUser(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value ="/infoUser")
    @ResponseBody
    public Optional<User> infoUser(@AuthenticationPrincipal User user_authentication){
        return userService.findByEmail(user_authentication.getEmail());
    }

    @GetMapping(value = "/findUser/{id}")
    public User findUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping(value ="/edit")
    public ResponseEntity<?> editUser(@RequestBody User user) {
        userService.updateUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/addUser")
    private ResponseEntity<User> addUser(@RequestBody User user) {
        userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        userService.removeUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
