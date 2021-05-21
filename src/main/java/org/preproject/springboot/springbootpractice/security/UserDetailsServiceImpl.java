package org.preproject.springboot.springbootpractice.security;

import org.preproject.springboot.springbootpractice.dao.UserDao;
import org.preproject.springboot.springbootpractice.model.Role;
import org.preproject.springboot.springbootpractice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Qualifier("UserDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserDao userDao;

    @Autowired
    public void setUserRepository(UserDao userDao){
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userDao.findByEmail(email);
        if(!user.isPresent()){
            throw new UsernameNotFoundException(String.format("Пользователь с таким email: '%s' не найден", email));
        }
        return user.get();
    }

    private Collection<? extends GrantedAuthority> mapRolesToGrantedAuthority(Collection<Role> roles) {
        return roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toList());
    }
}