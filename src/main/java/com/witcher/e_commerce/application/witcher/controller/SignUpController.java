package com.witcher.e_commerce.application.witcher.controller;

import com.witcher.e_commerce.application.witcher.entity.User;
import com.witcher.e_commerce.application.witcher.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignUpController {

    private UserService userService;

    @Autowired
    public SignUpController(UserService userService){
        this.userService=userService;
    }

    @InitBinder
    public void InitBinder(WebDataBinder dataBinder){

        StringTrimmerEditor stringTrimmerEditor=new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class,stringTrimmerEditor);
    }

    @GetMapping("/signup")
    public String signUpController(Model model){
        model.addAttribute("webUser", new User());

        return "signup";

    }

    @PostMapping("/processSignUpPage")
    public String processSignUpPage(
            @Valid @ModelAttribute("webUser") User theWebUser,
            BindingResult theBindingResult,
            Model theModel
    ){
        //form validation
        if (theBindingResult.hasErrors()){
            return "signup";
        }

        //to check the username is already taken
        User existingUser=userService.findByUsername(theWebUser.getUsername());
        if (existingUser!=null){
            theModel.addAttribute("webUser",new User());
            theModel.addAttribute("signupError","Username already exists!!!");
            return "landing";
        }

        //save the new user
        userService.save(theWebUser);
        return "landing";
    }

}
