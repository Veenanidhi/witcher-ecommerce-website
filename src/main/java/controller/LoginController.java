package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/signup")
    private String signUp(){
        return "signup";
    }

    @GetMapping("/login")
    private String login(){
        return "login";
    }


}
