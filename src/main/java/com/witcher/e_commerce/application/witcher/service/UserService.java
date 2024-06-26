package com.witcher.e_commerce.application.witcher.service;

import com.witcher.e_commerce.application.witcher.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    User findById(Long id);

    void save(User user);

    void saveUp(User user);

    List<User> findAll();

    void deleteById(Long id);

    User findByUsername(String username);


}
