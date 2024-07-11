package com.witcher.e_commerce.application.witcher.service;

import com.witcher.e_commerce.application.witcher.entity.User;
import com.witcher.e_commerce.application.witcher.entity.VerificationToken;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{

    User registerUser(User user);

    User findById(Long id);

    void deleteById(Long id);

    User findByUsername(String username);

    User findByEmail(String email);

    Boolean existsByUsername(String username);


    void verifyOtp(String number);

    //void save(User user, String token);

    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);

    User save(User user);

}
