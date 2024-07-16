package com.witcher.e_commerce.application.witcher.controller;


import com.witcher.e_commerce.application.witcher.entity.User;
import com.witcher.e_commerce.application.witcher.entity.VerificationToken;
import com.witcher.e_commerce.application.witcher.service.UserService;
import com.witcher.e_commerce.application.witcher.service.VerificationTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Controller
@Slf4j
public class AccountController {

    private final UserService userService;

    private final VerificationTokenService verificationTokenService;

    @Autowired
    public AccountController(UserService userService, VerificationTokenService verificationTokenService) {
        this.userService = userService;
        this.verificationTokenService = verificationTokenService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/activation")
    public String activation(@RequestParam("token") String token, Model model) {
        VerificationToken verificationToken = verificationTokenService.findByToken(token);
        if (verificationToken == null) {
            model.addAttribute("message", "Your verification token is invalid");
        } else {
            User user = verificationToken.getUser();
            if (user == null) {
                model.addAttribute("error", "No user associated with this verification token");
            } else {
                if (!user.isEnabled()) {
                    LocalDate currentDate = LocalDate.now();
                    LocalDate expiryDate = verificationToken.getExpiryDate().toInstant(ZoneOffset.UTC).atZone(ZoneId.systemDefault()).toLocalDate();

                    if (currentDate.isBefore(expiryDate) || currentDate.isEqual(expiryDate)) {
                        user.setEnabled(true);
                        userService.save(user);
                        model.addAttribute("message", "Your account has been activated successfully!");
                    } else {
                        model.addAttribute("error", "Your activation link has expired. Please request a new one.");
                    }
                } else {
                    model.addAttribute("message", "Your account is already activated.");
                }
            }
        }
        return "activation";
    }


}



