package com.witcher.e_commerce.application.witcher.service;

import com.witcher.e_commerce.application.witcher.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{

    User registerUser(User user);

    User findById(Long id);

    void deleteById(Long id);

    User findByUsername(String username);

    User findByEmail(String email);

    Boolean existsByUsername(String username);


    void verifyOtp(String number);


    User save(User user);



}
