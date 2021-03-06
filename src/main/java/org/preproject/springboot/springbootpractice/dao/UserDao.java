package org.preproject.springboot.springbootpractice.dao;

import org.preproject.springboot.springbootpractice.model.Role;
import org.preproject.springboot.springbootpractice.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    User getUserById(Long id);

    void saveUser(User user);

    void removeUserById(Long id);

    List<User> getAllUsers();

    void updateUser(User updatedUser, Long id);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    List<Role> getRoleList();

    Role getRole(String role);

    Role getRoleById(Long id);
}
