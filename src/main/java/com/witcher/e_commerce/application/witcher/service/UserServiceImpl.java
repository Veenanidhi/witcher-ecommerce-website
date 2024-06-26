package com.witcher.e_commerce.application.witcher.service;

import com.witcher.e_commerce.application.witcher.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class UserServieImpl implements UserService{
    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public void save(User user) {

    }

    @Override
    public void saveUp(User user) {

    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public User findByUsername(String username) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
