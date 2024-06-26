package com.witcher.e_commerce.application.witcher.service;

import com.witcher.e_commerce.application.witcher.entity.User;
import com.witcher.e_commerce.application.witcher.entity.VerificationToken;

public interface UserService{

    User registerUser(User user);

    User findById(Long id);

    void deleteById(Long id);

    User findByUsername(String username);

    Boolean existsByUsername(String username);


    void verifyOtp(String number);

    void save(User user, String token);

    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);


    void save(User user);
}
