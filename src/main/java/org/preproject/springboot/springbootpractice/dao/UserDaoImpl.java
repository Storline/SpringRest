package org.preproject.springboot.springbootpractice.dao;

import org.preproject.springboot.springbootpractice.model.Role;
import org.preproject.springboot.springbootpractice.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

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
        return em.createQuery("select u from User u left join fetch u.roles as roles", User.class)
                .getResultList();
    }

    @Override
    public void updateUser(Long id, User updatedUser) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(user);
    }

    @Override
    public List<Role> getRoleList() {
        return em
                .createQuery("select r from Role r", Role.class)
                .getResultList();
    }

    @Override
    public Role getRole(String role) {
        return em
                .createQuery("select r from Role r where r.role=:role", Role.class)
                .setParameter("role", role)
                .getSingleResult();
    }
}
