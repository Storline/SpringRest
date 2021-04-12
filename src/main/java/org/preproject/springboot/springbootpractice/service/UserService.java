package org.preproject.springboot.springbootpractice.service;

import org.preproject.springboot.springbootpractice.model.User;

import javax.management.relation.Role;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User getUserById(Long id);

    void saveUser(User user);

    void removeUserById(Long id);

    List<User> getAllUsers();

    void updateUser(Long id, User updatedUser);

    Optional<User> findByUsername(String username);

    List<Role> getRoleList();

    Role getRole(String role);
}
