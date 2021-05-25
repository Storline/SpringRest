package org.preproject.springboot.springbootpractice.dao;

import org.preproject.springboot.springbootpractice.model.Role;
import org.preproject.springboot.springbootpractice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserDaoImpl implements UserDao {

    private PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager em;

    @Override
    public User getUserById(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public void saveUser(User user) {
        em.persist(user);
    }

    @Override
    public void removeUserById(Long id) {
        em.createQuery("delete from User user where user.id =:id")
        .setParameter("id", id)
        .executeUpdate();
    }

    @Override
    public List<User> getAllUsers() {
        return em.createQuery("select user from User user inner join fetch user.roles as roles", User.class)
                .getResultList()
                .stream().distinct()
                .collect(Collectors.toList());
    }

    @Override
    public void updateUser(User updatedUser, Long id) {
        em.merge(updatedUser);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        User user = null;
        try{
            user =  (User) em.createQuery("select user from User user inner join fetch user.roles as roles where user.username = :username")
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        User user = null;
        try{
            user =  (User) em.createQuery("select user from User user inner join fetch user.roles as roles where user.email = :email")
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException ignored) {
        }
        return Optional.ofNullable(user);
    }

    @Override
    public List<Role> getRoleList() {
        return em
                .createQuery("select u from Role u", Role.class)
                .getResultList();
    }
    @Override
    public Role getRole(String role) {
        return em
                .createQuery("select u from Role u where u.role=:role", Role.class)
                .setParameter("role", role)
                .getSingleResult();
    }

    @Override
    public Role getRoleById(Long id){
        return em
                .createQuery("select u from Role u where u.id=:id", Role.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
