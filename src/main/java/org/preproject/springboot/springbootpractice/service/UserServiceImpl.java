package org.preproject.springboot.springbootpractice.service;

import org.preproject.springboot.springbootpractice.dao.UserDao;
import org.preproject.springboot.springbootpractice.model.Role;
import org.preproject.springboot.springbootpractice.model.User;
import org.preproject.springboot.springbootpractice.util.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("userDetailsService")
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserDao service;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserDao service){
        this.service = service;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", email)));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }


    @Override
    public User getUserById(Long id) {
        return service.getUserById(id);
    }

    @Override
    public Optional<User> findByUsername(String username){return service.findByUsername(username);}

    @Override
    public Optional<User> findByEmail(String email){return service.findByEmail(email);}

    @Override
    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        service.saveUser(user);
    }

    @Override
    @Transactional
    public void removeUserById(Long id) {
        service.removeUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    @Override
    @Transactional
    public void updateUser(User updatedUser) {
        updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        service.updateUser(updatedUser);
    }

    @Override
    public List<Role> getRoleList() {
        return service.getRoleList();
    }

    @Override
    public Role getRole(String role) {
        return service.getRole(role);
    }

    @Override
    public Role getRoleById(Long id){
        return service.getRoleById(id);
    }

    @Override
    public Map<String, String> validateUser(User user) {
        Map<String, String> errorMap = new HashMap<>();
        if (user.getEmail().isEmpty()) {
            errorMap.put("emailIsEmpty", "E-Mail Is Empty!");
        }
        if (!EmailValidator.validateEmail(user.getEmail())) {
            errorMap.put("emailNotValid", "Email Not Valid!");
        }
        else if (service.findByEmail(user.getEmail()).isPresent()) {
            errorMap.put("userExists", "An account for that email already exists.");
        }
        if (user.getName().isEmpty())
            errorMap.put("firstNameIsEmpty", "First Name Is Empty!");
        if (user.getLastName().isEmpty())
            errorMap.put("lastNameIsEmpty", "Last Name Is Empty!");
        if (user.getPassword().isEmpty())
            errorMap.put("passwordIsEmpty", "Password Is Empty!");
        return errorMap;
    }

}
