package com.witcher.e_commerce.application.witcher.service;

import com.witcher.e_commerce.application.witcher.dao.UserRepository;
import com.witcher.e_commerce.application.witcher.dao.VerificationTokenRepository;
import com.witcher.e_commerce.application.witcher.entity.User;
import com.witcher.e_commerce.application.witcher.entity.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final VerificationTokenRepository tokenRepository;

    private EmailService emailService;



    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder, VerificationTokenRepository tokenRepository, EmailService emailService){
        this.userRepository=userRepository;
        this.passwordEncoder=bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
        this.emailService=emailService;

    }

    @Override
    public User findById(Long id) {
        Optional<User>user1=userRepository.findById(id);
        if (user1.isPresent()){
            return user1.get();
        }

        return null;
    }

    @Override
    public User registerUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (userRepository.count() >1){
            user.setRole(Role.ROLE_USER);
        }
      else{
          user.setRole(Role.ROLE_ADMIN);
        }
         userRepository.save(user);

      User user1=new User();
      //to disable new user before activation
      user1.setEnabled(false);

        Optional<User> saved = Optional.of(save(user1));

// Create and save verification token if the user is saved
        saved.ifPresent(u -> {
            try {
                String token = UUID.randomUUID().toString();
                VerificationToken verificationToken = new VerificationToken(token,u);
                tokenRepository.save(verificationToken);

                // Send verification email
                emailService.sendHtmlMail(u);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return saved.get();
    }


    @Override
    public void deleteById(Long id) {
      userRepository.deleteById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();

    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public void verifyOtp(String number) {

    }


    @Transactional
    public VerificationToken findByToken(String token){
        return tokenRepository.findByToken(token);
    }
    @Transactional
    public VerificationToken findByUser(User user){
        return tokenRepository.findByUser(user);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }


    @Override
    public void save(User user, String token) {
        VerificationToken verificationToken=new VerificationToken(token, user);
        //set expiry date to 24 hrs
        verificationToken.setExpiryDate(calculatedExpiryDate(24*60));
        tokenRepository.save(verificationToken);

    }

    //calculate expiry date
    private Timestamp calculatedExpiryDate(int expiryTimeInMinutes){
        Calendar calendar= Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Timestamp(calendar.getTime().getTime());
    }










}
