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
public class UserServiceImpl implements UserService {

    private final UserDao service;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserDao service){
        this.service = service;
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
    public void updateUser(User updatedUser, Long id) {
        updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        service.updateUser(updatedUser, id);
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
    public boolean isUniqueEmailViolated(Long id, String email) {
        boolean isUniqueEmailViolated = false;

        Optional<User> findUserByEmail = service.findByEmail(email);
        boolean isCreatingNew = (id == null || id == 0);

        if (isCreatingNew){
            if(findUserByEmail.isPresent()) isUniqueEmailViolated = true;
        } else {
            if(findUserByEmail.isPresent()) {
                if (findUserByEmail.get().getId().equals(id)) {
                    isUniqueEmailViolated = true;
                }
            }
        }
        return isUniqueEmailViolated;
    }

}
