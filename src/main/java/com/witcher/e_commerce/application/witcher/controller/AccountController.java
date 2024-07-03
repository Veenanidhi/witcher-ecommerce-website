package com.witcher.e_commerce.application.witcher.controller;


import com.witcher.e_commerce.application.witcher.entity.User;
import com.witcher.e_commerce.application.witcher.entity.VerificationToken;
import com.witcher.e_commerce.application.witcher.service.TokenService;
import com.witcher.e_commerce.application.witcher.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;

@Controller
@Slf4j
public class AccountController {

    private final UserService userService;

    private final TokenService tokenService;

    public AccountController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;

    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }


    @GetMapping("/verify")
    public String activation(@RequestParam("token") String token, Model model) {

        //create html pge for activation

        VerificationToken verificationToken1 = verificationToken(token);
        if ((verificationToken1 == null)) {
            model.addAttribute("message", "your verification token is invalid");
        } else {
            User user = verificationToken1.getUser();

            // if the user acc is not activated
            if (!user.isEnabled()) {
                //to get the current timestamp
                Timestamp currentTimeStamp = new Timestamp(System.currentTimeMillis());
                //check if the token is expired
                if (verificationToken1.getExpiryDate().before(currentTimeStamp)) {
                    model.addAttribute("message", "your verification token has expired:(");
                } else {
                    //token is valid
                    //activate the user acc
                    user.setEnabled(true);
                    //update the user
                    userService.save(user);
                    model.addAttribute("message", "your account activated succesfully!!");
                }

            } else {
                //the user acc is already activated
                model.addAttribute("message", "your acc is already activated");
            }
        }
        //add /activation to securityconfig

        return "activation";
    }

    private VerificationToken verificationToken(String token) {
        return tokenService.verificationToken(token);
    }
}