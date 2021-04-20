package org.preproject.springboot.springbootpractice.service;

import org.preproject.springboot.springbootpractice.dao.UserDao;
import org.preproject.springboot.springbootpractice.model.Role;
import org.preproject.springboot.springbootpractice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        user.setRoles(Collections.singleton(new Role()));
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
    public void updateUser(Long id, User updatedUser) {
        updatedUser.setRoles(Collections.singleton(new Role()));
        updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        service.updateUser(id, updatedUser);
    }

    @Override
    public List<Role> getRoleList() {
        return service.getRoleList();
    }

    @Override
    public Role getRole(String role) {
        return service.getRole(role);
    }

}
