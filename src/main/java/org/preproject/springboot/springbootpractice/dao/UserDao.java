package org.preproject.springboot.springbootpractice.dao;

import org.preproject.springboot.springbootpractice.model.Role;
import org.preproject.springboot.springbootpractice.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    User getUserById(Long id);

    void saveUser(User user);

    void removeUserById(Long id);

    List<User> getAllUsers();

    void updateUser(Long id, User updatedUser);

    Optional<User> findByUsername(String username);

    List<Role> getRoleList();

    Role getRole(String role);
}
