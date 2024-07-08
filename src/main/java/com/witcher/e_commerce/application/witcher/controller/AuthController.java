package com.witcher.e_commerce.application.witcher.controller;

import com.witcher.e_commerce.application.witcher.entity.User;
import com.witcher.e_commerce.application.witcher.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {


    private final UserService userService;

    @Autowired
    public AuthController(UserService userService){
        this.userService=userService;
    }


   /* @GetMapping("/login")
    public String loginPage(HttpServletRequest request, Principal principal, Model model) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Object authenticatedAttribute = session.getAttribute("authenticated");
            if (authenticatedAttribute != null && (Boolean) authenticatedAttribute) {
                return "redirect:/"; // Redirect authenticated users away from the login page
            }
            session.removeAttribute("registration");
        }
        return "login";
    }*/

    @GetMapping("/login")
    public String showLogin(){

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

        if(authentication==null || authentication instanceof AnonymousAuthenticationToken){
            return "login";
        }

        return "admin/categories";

    }

     @GetMapping("/product-list")
    public String getProductList(){
        return "product-listing";
    }

  /*  @GetMapping("/categories")
    public String getCategory(){
        return "categories";
    }
*/

    @GetMapping("/landing")
    public String dashboard() {
        return "landing";
        }

    @GetMapping("/signup")
    public String signUpController(Model model){
        model.addAttribute("webUser", new User());
        userService.verifyOtp("1234");
        return "signup";

    }

    @PostMapping("/processSignUpPage")
    public String processSignUpPage(
            @ModelAttribute("webUser") User theWebUser,
            BindingResult theBindingResult, RedirectAttributes ra,
            Model theModel
    ){


        //to check the username is already taken
        Boolean existingUser=userService.existsByUsername(theWebUser.getUsername());
        if (existingUser){
            theModel.addAttribute("webUser",new User());
            theModel.addAttribute("signupError","Username already exists!!!");
            return "redirect:/signup";
        }

        //save the new user
        userService.registerUser(theWebUser);
        ra.addFlashAttribute("message","an activation mail is send to ur mail");
        return "redirect:/login";
    }

    @GetMapping("/access-denied")
    public String showAccessDenied() {

        return "access-denied";
    }

    

}
