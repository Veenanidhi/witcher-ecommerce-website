package com.witcher.e_commerce.application.witcher.service;

import com.witcher.e_commerce.application.witcher.dao.UserRepository;
import com.witcher.e_commerce.application.witcher.dao.VerificationTokenRepository;
import com.witcher.e_commerce.application.witcher.entity.User;
import com.witcher.e_commerce.application.witcher.entity.VerificationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
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
    @Transactional
    public User registerUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (userRepository.count() >1){
            user.setRole(Role.ROLE_USER);
        }
      else{
          user.setRole(Role.ROLE_ADMIN);
        }

      //to disable new user before activation
      user.setEnabled(false);
      log.info("USER BEFORE SAVING :{}",user);
      Optional<User> saved = Optional.of(save(user));

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

    // To set expiry date
    public void setExpiry(User user, String token) {
       VerificationToken verificationToken= new VerificationToken(token, user);

       Calendar cal= Calendar.getInstance();
       cal.add(Calendar.HOUR, 24);
       verificationToken.setExpiryDate(new Timestamp(cal.getTime().getTime()));

       tokenRepository.save(verificationToken);
    }

    public VerificationResult verifyToken(String token){
        VerificationToken verificationToken=tokenRepository.findByToken(token);

        if (verificationToken == null) {
            return VerificationResult.TOKEN_INVALID;
        }

        if (new Date().after(verificationToken.getExpiryDate())) {
            return VerificationResult.TOKEN_EXPIRED;
        }
            // Token is valid and not expired, proceed with user verification
            User user = verificationToken.getUser();
            user.setEnabled(true);
            // Update user in database
            // Remove or invalidate the used token

            return VerificationResult.TOKEN_VALID;
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
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
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

/*
    @Override
    public void save(User user, String token) {
        VerificationToken verificationToken=new VerificationToken(token, user);
        //set expiry date to 24 hrs
        verificationToken.setExpiryDate(calculatedExpiryDate(24*60));
        tokenRepository.save(verificationToken);

    }

 */

    //calculate expiry date
    private Timestamp calculatedExpiryDate(int expiryTimeInMinutes){
        Calendar calendar= Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Timestamp(calendar.getTime().getTime());
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional= userRepository.findByEmail(email);
        if (userOptional.isEmpty()){
            throw new UsernameNotFoundException("User not found with this email" +email);
        }
        User user= userOptional.get();

        return new CustomUserDetails(user);
    }
}
