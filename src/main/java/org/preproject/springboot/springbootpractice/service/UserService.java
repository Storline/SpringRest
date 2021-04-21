package org.preproject.springboot.springbootpractice.service;

import org.preproject.springboot.springbootpractice.model.Role;
import org.preproject.springboot.springbootpractice.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User getUserById(Long id);

    void saveUser(User user);

    void removeUserById(Long id);

    List<User> getAllUsers();

    void updateUser(User updatedUser);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    List<Role> getRoleList();

    Role getRole(String role);
}
